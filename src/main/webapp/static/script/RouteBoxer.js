
function RouteBoxer() {
  this.R = 6371; // earth's mean radius in km
}

RouteBoxer.prototype.box = function (path, range) {

  this.grid_ = null;

  this.latGrid_ = [];
  
  this.lngGrid_ = [];
  
  this.boxesX_ = [];

  this.boxesY_ = [];
  
  var vertices = null;

  if (path instanceof Array) {
    vertices = path;
  } else if (path instanceof google.maps.Polyline) {
    if (path.getPath) {
      vertices = new Array(path.getPath().getLength());
      for (var i = 0; i < vertices.length; i++) {
        vertices[i] = path.getPath().getAt(i);
      }
    } else {
      vertices = new Array(path.getVertexCount());
      for (var j = 0; j < vertices.length; j++) {
        vertices[j] = path.getVertex(j);
      }
    }
  }

  this.buildGrid_(vertices, range);
  
  this.findIntersectingCells_(vertices);
  
  this.mergeIntersectingCells_();

  return (this.boxesX_.length <= this.boxesY_.length ?
          this.boxesX_ :
          this.boxesY_);
};

RouteBoxer.prototype.buildGrid_ = function (vertices, range) {

  var routeBounds = new google.maps.LatLngBounds();
  for (var i = 0; i < vertices.length; i++) {
    routeBounds.extend(vertices[i]);
  }
  
  var routeBoundsCenter = routeBounds.getCenter();
  
  this.latGrid_.push(routeBoundsCenter.lat());
  
  this.latGrid_.push(routeBoundsCenter.rhumbDestinationPoint(0, range).lat());
  for (i = 2; this.latGrid_[i - 2] < routeBounds.getNorthEast().lat(); i++) {
    this.latGrid_.push(routeBoundsCenter.rhumbDestinationPoint(0, range * i).lat());
  }

  for (i = 1; this.latGrid_[1] > routeBounds.getSouthWest().lat(); i++) {
    this.latGrid_.unshift(routeBoundsCenter.rhumbDestinationPoint(180, range * i).lat());
  }

  this.lngGrid_.push(routeBoundsCenter.lng());
  
  this.lngGrid_.push(routeBoundsCenter.rhumbDestinationPoint(90, range).lng());
  for (i = 2; this.lngGrid_[i - 2] < routeBounds.getNorthEast().lng(); i++) {
    this.lngGrid_.push(routeBoundsCenter.rhumbDestinationPoint(90, range * i).lng());
  }
  
  for (i = 1; this.lngGrid_[1] > routeBounds.getSouthWest().lng(); i++) {
    this.lngGrid_.unshift(routeBoundsCenter.rhumbDestinationPoint(270, range * i).lng());
  }
  
  this.grid_ = new Array(this.lngGrid_.length);
  for (i = 0; i < this.grid_.length; i++) {
    this.grid_[i] = new Array(this.latGrid_.length);
  }
};

RouteBoxer.prototype.findIntersectingCells_ = function (vertices) {
  var hintXY = this.getCellCoords_(vertices[0]);
  
  this.markCell_(hintXY);

  for (var i = 1; i < vertices.length; i++) {
    var gridXY = this.getGridCoordsFromHint_(vertices[i], vertices[i - 1], hintXY);
    
    if (gridXY[0] === hintXY[0] && gridXY[1] === hintXY[1]) {
      continue;
    
    } else if ((Math.abs(hintXY[0] - gridXY[0]) === 1 && hintXY[1] === gridXY[1]) ||
        (hintXY[0] === gridXY[0] && Math.abs(hintXY[1] - gridXY[1]) === 1)) {
      this.markCell_(gridXY);
      
    } else {
      this.getGridIntersects_(vertices[i - 1], vertices[i], hintXY, gridXY);
    }
    
    hintXY = gridXY;
  }
};

RouteBoxer.prototype.getCellCoords_ = function (latlng) {
  for (var x = 0; this.lngGrid_[x] < latlng.lng(); x++) {}
  for (var y = 0; this.latGrid_[y] < latlng.lat(); y++) {}
  return ([x - 1, y - 1]);
};

RouteBoxer.prototype.getGridCoordsFromHint_ = function (latlng, hintlatlng, hint) {
  var x, y;
  if (latlng.lng() > hintlatlng.lng()) {
    for (x = hint[0]; this.lngGrid_[x + 1] < latlng.lng(); x++) {}
  } else {
    for (x = hint[0]; this.lngGrid_[x] > latlng.lng(); x--) {}
  }
  
  if (latlng.lat() > hintlatlng.lat()) {
    for (y = hint[1]; this.latGrid_[y + 1] < latlng.lat(); y++) {}
  } else {        
    for (y = hint[1]; this.latGrid_[y] > latlng.lat(); y--) {}
  }
  
  return ([x, y]);
};

RouteBoxer.prototype.getGridIntersects_ = function (start, end, startXY, endXY) {
  var edgePoint, edgeXY, i;
  var brng = start.rhumbBearingTo(end);         // Step 1.
  
  var hint = start;
  var hintXY = startXY;
  
  if (end.lat() > start.lat()) {
    for (i = startXY[1] + 1; i <= endXY[1]; i++) {
      edgePoint = this.getGridIntersect_(start, brng, this.latGrid_[i]);
      
      edgeXY = this.getGridCoordsFromHint_(edgePoint, hint, hintXY);
      
      this.fillInGridSquares_(hintXY[0], edgeXY[0], i - 1);
      
      hint = edgePoint;
      hintXY = edgeXY;
    }
    
    this.fillInGridSquares_(hintXY[0], endXY[0], i - 1);
    
  } else {
    for (i = startXY[1]; i > endXY[1]; i--) {
      edgePoint = this.getGridIntersect_(start, brng, this.latGrid_[i]);
      
      edgeXY = this.getGridCoordsFromHint_(edgePoint, hint, hintXY);

      this.fillInGridSquares_(hintXY[0], edgeXY[0], i);

      hint = edgePoint;
      hintXY = edgeXY;
    }
    
    this.fillInGridSquares_(hintXY[0], endXY[0], i);    
  }
};

RouteBoxer.prototype.getGridIntersect_ = function (start, brng, gridLineLat) {
  var d = this.R * ((gridLineLat.toRad() - start.lat().toRad()) / Math.cos(brng.toRad()));
  return start.rhumbDestinationPoint(brng, d);
};

RouteBoxer.prototype.fillInGridSquares_ = function (startx, endx, y) {
  var x;
  if (startx < endx) {
    for (x = startx; x <= endx; x++) {
      this.markCell_([x, y]);
    }
  } else {
    for (x = startx; x >= endx; x--) {
      this.markCell_([x, y]);
    }            
  }      
};

RouteBoxer.prototype.markCell_ = function (cell) {
  var x = cell[0];
  var y = cell[1];
  this.grid_[x - 1][y - 1] = 1;
  this.grid_[x][y - 1] = 1;
  this.grid_[x + 1][y - 1] = 1;
  this.grid_[x - 1][y] = 1;
  this.grid_[x][y] = 1;
  this.grid_[x + 1][y] = 1;
  this.grid_[x - 1][y + 1] = 1;
  this.grid_[x][y + 1] = 1;
  this.grid_[x + 1][y + 1] = 1;
};

RouteBoxer.prototype.mergeIntersectingCells_ = function () {
  var x, y, box;
  
  var currentBox = null;
  
  for (y = 0; y < this.grid_[0].length; y++) {
    for (x = 0; x < this.grid_.length; x++) {
      
      if (this.grid_[x][y]) {
        box = this.getCellBounds_([x, y]);
        if (currentBox) {
          currentBox.extend(box.getNorthEast());
        } else {
          currentBox = box;
        }
        
      } else {
        this.mergeBoxesY_(currentBox);
        currentBox = null;
      }
    }
    this.mergeBoxesY_(currentBox);
    currentBox = null;
  }

  for (x = 0; x < this.grid_.length; x++) {
    for (y = 0; y < this.grid_[0].length; y++) {
      if (this.grid_[x][y]) {
        
        if (currentBox) {
          box = this.getCellBounds_([x, y]);
          currentBox.extend(box.getNorthEast());
        } else {
          currentBox = this.getCellBounds_([x, y]);
        }
        
      } else {
        this.mergeBoxesX_(currentBox);
        currentBox = null;
        
      }
    }
    this.mergeBoxesX_(currentBox);
    currentBox = null;
  }
};

RouteBoxer.prototype.mergeBoxesX_ = function (box) {
  if (box !== null) {
    for (var i = 0; i < this.boxesX_.length; i++) {
      if (this.boxesX_[i].getNorthEast().lng() === box.getSouthWest().lng() &&
          this.boxesX_[i].getSouthWest().lat() === box.getSouthWest().lat() &&
          this.boxesX_[i].getNorthEast().lat() === box.getNorthEast().lat()) {
        this.boxesX_[i].extend(box.getNorthEast());
        return;
      }
    }
    this.boxesX_.push(box);
  }
};

RouteBoxer.prototype.mergeBoxesY_ = function (box) {
  if (box !== null) {
    for (var i = 0; i < this.boxesY_.length; i++) {
      if (this.boxesY_[i].getNorthEast().lat() === box.getSouthWest().lat() &&
          this.boxesY_[i].getSouthWest().lng() === box.getSouthWest().lng() &&
          this.boxesY_[i].getNorthEast().lng() === box.getNorthEast().lng()) {
        this.boxesY_[i].extend(box.getNorthEast());
        return;
      }
    }
    this.boxesY_.push(box);
  }
};

RouteBoxer.prototype.getCellBounds_ = function (cell) {
  return new google.maps.LatLngBounds(
    new google.maps.LatLng(this.latGrid_[cell[1]], this.lngGrid_[cell[0]]),
    new google.maps.LatLng(this.latGrid_[cell[1] + 1], this.lngGrid_[cell[0] + 1]));
};

google.maps.LatLng.prototype.rhumbDestinationPoint = function (brng, dist) {
  var R = 6371; // earth's mean radius in km
  var d = parseFloat(dist) / R;  // d = angular distance covered on earth's surface
  var lat1 = this.lat().toRad(), lon1 = this.lng().toRad();
  brng = brng.toRad();

  var lat2 = lat1 + d * Math.cos(brng);
  var dLat = lat2 - lat1;
  var dPhi = Math.log(Math.tan(lat2 / 2 + Math.PI / 4) / Math.tan(lat1 / 2 + Math.PI / 4));
  var q = (Math.abs(dLat) > 1e-10) ? dLat / dPhi : Math.cos(lat1);
  var dLon = d * Math.sin(brng) / q;

  if (Math.abs(lat2) > Math.PI / 2) {
    lat2 = lat2 > 0 ? Math.PI - lat2 : - (Math.PI - lat2);
  }
  var lon2 = (lon1 + dLon + Math.PI) % (2 * Math.PI) - Math.PI;
 
  if (isNaN(lat2) || isNaN(lon2)) {
    return null;
  }
  return new google.maps.LatLng(lat2.toDeg(), lon2.toDeg());
};

google.maps.LatLng.prototype.rhumbBearingTo = function (dest) {
  var dLon = (dest.lng() - this.lng()).toRad();
  var dPhi = Math.log(Math.tan(dest.lat().toRad() / 2 + Math.PI / 4) / Math.tan(this.lat().toRad() / 2 + Math.PI / 4));
  if (Math.abs(dLon) > Math.PI) {
    dLon = dLon > 0 ? -(2 * Math.PI - dLon) : (2 * Math.PI + dLon);
  }
  return Math.atan2(dLon, dPhi).toBrng();
};

Number.prototype.toRad = function () {
  return this * Math.PI / 180;
};

Number.prototype.toDeg = function () {
  return this * 180 / Math.PI;
};

Number.prototype.toBrng = function () {
  return (this.toDeg() + 360) % 360;
};
