
var polygons = [];
var circleCenter;
var circleRadius;
var editpolyline;
var editpolygon;
var editcircle;

var messages = {
    'ERROR_1':'Please enter the Name'
};

function editline() {
    var types = [google.maps.MapTypeId.ROADMAP, google.maps.MapTypeId.HYBRID, google.maps.MapTypeId.SATELLITE];
    var myOptions = {
        center: new google.maps.LatLng(28.612864, 77.231119),
        mapTypeControlOptions: {
            mapTypeIds: types,
            style: google.maps.MapTypeControlStyle.DROPDOWN_MENU
        },
        mapTypeId: google.maps.MapTypeId.ROADMAP,
        panControl: false,
        scaleControl: true,
        streetViewControl: false,
        zoom: 14,
        zoomControl: true
    };

    var map = new google.maps.Map(document.getElementById('map_canvas'), myOptions);

    var editpolyline = new google.maps.Polyline({
        path: polylineCoords,
        geodesic: true,
        strokeColor: '#FF0000',
        strokeOpacity: 1.0,
        strokeWeight: 2
    });

    editpolyline.setMap(map);

    var bounds = new google.maps.LatLngBounds();
    for (var i=0; i<polylineCoords.length; i++) {
        bounds.extend(polylineCoords[i]);
    }
    map.fitBounds(bounds);
}

function initgeo() {

    var types = [google.maps.MapTypeId.ROADMAP, google.maps.MapTypeId.HYBRID, google.maps.MapTypeId.SATELLITE];
    var myOptions = {
        center: new google.maps.LatLng(28.612864, 77.231119),
        mapTypeControlOptions: {
            mapTypeIds: types,
            style: google.maps.MapTypeControlStyle.DROPDOWN_MENU
        },
        mapTypeId: google.maps.MapTypeId.ROADMAP,
        panControl: false,
        scaleControl: true,
        streetViewControl: false,
        zoom: 14,
        zoomControl: true
    };

    var map = new google.maps.Map(document.getElementById('map_canvas'), myOptions);

    var drawingManager = new google.maps.drawing.DrawingManager({
        drawingMode: google.maps.drawing.OverlayType.POLYGON,
        drawingControl: true,
        drawingControlOptions: {
            position: google.maps.ControlPosition.TOP_CENTER,
            drawingModes: [google.maps.drawing.OverlayType.POLYGON]
        },
        polygonOptions: {
            fillColor: '#FFFF00',
            fillOpacity: 0.25,
            strokeWeight: 5,
            strokeOpacity: 1.0,
            clickable: false,
            editable: true,
            zIndex: 1
        },
        map: map
    });

    var htmlcontent = '';
    htmlcontent += '<div style="padding:5px;">';
    htmlcontent += '<div style="color:#000000; font-size:1.2em; font-weight:bold;">Create Geozone</div>';
    htmlcontent += '<div style="color:#FF0000;" id="errortext">&nbsp;</div>';
    htmlcontent += '<div>';
    htmlcontent += '<table>';
    htmlcontent += '<tr>';
    htmlcontent += '<td style="text-align:left; padding-top:5px;">Name:&nbsp;</td>';
    htmlcontent += '<td style="text-align:left; padding-top:5px;"><input type="text" id="name" name="name" size="20" style="border:1px solid #003264; width:160px;" /></td>';
    htmlcontent += '</tr>';
    htmlcontent += '<tr>';
    htmlcontent += '<td style="text-align:left; padding-top:5px;">Description:&nbsp;</td>';
    htmlcontent += '<td style="text-align:left; padding-top:5px;"><input type="text" id="description" name="description" size="20" style="border:1px solid #003264; width:160px;" /></td>';
    htmlcontent += '</tr>';
    htmlcontent += '<tr>';
    htmlcontent += '<td style="text-align:left; padding-top:5px;">&nbsp;</td>';
    htmlcontent += '<td style="text-align:left; padding-top:5px;"><input type="submit" value="Submit" onclick="createGeo()" style="border:1px solid #003264; width:80px;" /></td>';
    htmlcontent += '</tr>';
    htmlcontent += '</table>';
    htmlcontent += '</div>';
    htmlcontent += '</div>';

    var infowindow = new google.maps.InfoWindow({
        content: htmlcontent,
        zIndex: 100
    });
    
    var geozone = null;
    google.maps.event.addListener(drawingManager, 'polygoncomplete', function (polygon) {
        drawingManager.setDrawingMode(null);
        geozone = polygon;

        var paths = polygon.getPath();
        for (var i = 0; i < paths.length; i++) {
            polygons.push(paths.getAt(i));
        }
        infowindow.setPosition(paths.getAt(0));
        infowindow.open(map);
    });

    google.maps.event.addListener(infowindow,'closeclick', function() {
        geozone.setMap(null);
        drawingManager.setDrawingMode(google.maps.drawing.OverlayType.POLYGON);
    });

    google.maps.event.addListener(map,'click', function() {
        infowindow.setMap(null);
        geozone.setMap(null);
        drawingManager.setDrawingMode(google.maps.drawing.OverlayType.POLYGON);
    });
}

function createGeo() {

    var name = $('#name').val();
    var description = $('#description').val();
    
    $('#errortext').html("&nbsp;");
    
    if(!validate( name )) {
        return false;
    }

    var ajaxurl = appURL + "/draw/polygon.html";
    var ajaxqry = "name=" + name + "&description=" + description + "&polygons=" + polygons;
    
    $.support.cors = true;
    $.ajax({
        type: "GET",
        url: ajaxurl,
        data: ajaxqry,
        processData: false,
        error: function (err) {
            alert(err.statusText);
        },
        success: function (res) {
            var ctl = res.substring(0, 1);
            if(ctl == 0) {
                $('#errortext').html(res.substring(1, res.length));
            } else {
                window.location = appURL + "/geozone/manage.html?id=" + res;
            }
        }
    });
}

function editgeo() {
    var types = [google.maps.MapTypeId.ROADMAP, google.maps.MapTypeId.HYBRID, google.maps.MapTypeId.SATELLITE];
    var myOptions = {
        center: new google.maps.LatLng(28.612864, 77.231119),
        mapTypeControlOptions: {
            mapTypeIds: types,
            style: google.maps.MapTypeControlStyle.DROPDOWN_MENU
        },
        mapTypeId: google.maps.MapTypeId.ROADMAP,
        panControl: false,
        scaleControl: true,
        streetViewControl: false,
        zoom: 14,
        zoomControl: true
    };

    var map = new google.maps.Map(document.getElementById('map_canvas'), myOptions);

    editpolygon = new google.maps.Polygon({
        editable: false,
        paths: polygonCoords,
        strokeColor: "#FF0000",
        strokeOpacity: 1.0,
        strokeWeight: 5,
        fillColor: "#FF0000",
        fillOpacity: 0.25
    });

    editpolygon.setMap(map);
    
    var bounds = new google.maps.LatLngBounds();
    for (var i=0; i<polygonCoords.length; i++) {
        bounds.extend(polygonCoords[i]);
    }
    map.fitBounds(bounds);
}

function initpoi() {
    
    var types = [google.maps.MapTypeId.ROADMAP, google.maps.MapTypeId.HYBRID, google.maps.MapTypeId.SATELLITE];
    var myOptions = {
        center: new google.maps.LatLng(28.612864, 77.231119),
        mapTypeControlOptions: {
            mapTypeIds: types,
            style: google.maps.MapTypeControlStyle.DROPDOWN_MENU
        },
        mapTypeId: google.maps.MapTypeId.ROADMAP,
        panControl: false,
        scaleControl: true,
        streetViewControl: false,
        zoom: 14,
        zoomControl: true
    };
    var map = new google.maps.Map(document.getElementById('map_canvas'), myOptions);

    var drawingManager = new google.maps.drawing.DrawingManager({
        drawingMode: google.maps.drawing.OverlayType.CIRCLE,
        drawingControl: true,
        drawingControlOptions: {
            position: google.maps.ControlPosition.TOP_CENTER,
            drawingModes: [google.maps.drawing.OverlayType.CIRCLE]
        },
        circleOptions: {
            fillColor: '#FFFF00',
            fillOpacity: 0.25,
            strokeWeight: 5,
            strokeOpacity: 1.0,
            clickable: false,
            editable: true,
            zIndex: 1
        },
        map: map
    });

    var htmlcontent = '';
    htmlcontent += '<div style="padding:5px;">';
    htmlcontent += '<div style="color:#000000; font-size:1.2em; font-weight:bold;">Create Landmark</div>';
    htmlcontent += '<div style="color:#FF0000;" id="errortext">&nbsp;</div>';
    htmlcontent += '<div>';
    htmlcontent += '<table>';
    htmlcontent += '<tr>';
    htmlcontent += '<td style="text-align:left; padding-top:5px;">Name:&nbsp;</td>';
    htmlcontent += '<td style="text-align:left; padding-top:5px;"><input type="text" id="name" name="name" size="20" style="border:1px solid #003264; width:160px;" /></td>';
    htmlcontent += '</tr>';
    htmlcontent += '<tr>';
    htmlcontent += '<td style="text-align:left; padding-top:5px;">Description:&nbsp;</td>';
    htmlcontent += '<td style="text-align:left; padding-top:5px;"><input type="text" id="description" name="description" size="20" style="border:1px solid #003264; width:160px;" /></td>';
    htmlcontent += '</tr>';
    htmlcontent += '<tr>';
    htmlcontent += '<td style="text-align:left; padding-top:5px;">&nbsp;</td>';
    htmlcontent += '<td style="text-align:left; padding-top:5px;"><input type="submit" value="Submit" onclick="createPOI()" style="border:1px solid #003264; width:80px;" /></td>';
    htmlcontent += '</tr>';
    htmlcontent += '</table>';
    htmlcontent += '</div>';
    htmlcontent += '</div>';

    var infowindow = new google.maps.InfoWindow({
        content: htmlcontent,
        zIndex: 100
    });

    var landmark = null;
    google.maps.event.addListener(drawingManager, 'circlecomplete', function (circle) {
        drawingManager.setDrawingMode(null);
        landmark = circle;

        circleCenter = circle.getCenter();
        circleRadius = circle.getRadius();

        infowindow.setPosition(circleCenter);
        infowindow.open(map);
    });

    google.maps.event.addListener(infowindow,'closeclick', function() {
        landmark.setMap(null);
        drawingManager.setDrawingMode(google.maps.drawing.OverlayType.CIRCLE);
    });

    google.maps.event.addListener(map,'click', function() {
        infowindow.setMap(null);
        landmark.setMap(null);
        drawingManager.setDrawingMode(google.maps.drawing.OverlayType.CIRCLE);
    });
}

function createPOI() {

    var name = $('#name').val();
    var description = $('#description').val();

    $('#errortext').html("&nbsp;");

    if(!validate( name )) {
        return false;
    }

    var ajaxurl = appURL + "/draw/circle.html";
    var ajaxqry = "name=" + name + "&description=" + description + "&centre=" + circleCenter + "&radius=" + circleRadius;

    $.support.cors = true;
    $.ajax({
        type: "GET",
        url: ajaxurl,
        data: ajaxqry,
        processData: false,
        error: function (err) {
            alert(err.statusText);
        },
        success: function (res) {
            var ctl = res.substring(0, 1);
            if(ctl == 0) {
                $('#errortext').html(res.substring(1, res.length));
            } else {
                window.location = appURL + "/landmark/manage.html?id=" + res;
            }
        }
    });
}

function editpoi() {
    var types = [google.maps.MapTypeId.ROADMAP, google.maps.MapTypeId.HYBRID, google.maps.MapTypeId.SATELLITE];
    var myOptions = {
        center: new google.maps.LatLng(28.612864, 77.231119),
        mapTypeControlOptions: {
            mapTypeIds: types,
            style: google.maps.MapTypeControlStyle.DROPDOWN_MENU
        },
        mapTypeId: google.maps.MapTypeId.ROADMAP,
        panControl: false,
        scaleControl: true,
        streetViewControl: false,
        zoom: 14,
        zoomControl: true
    };

    var map = new google.maps.Map(document.getElementById('map_canvas'), myOptions);

    editcircle = new google.maps.Circle({
        editable: false,
        center: poicentre,
        radius: poiradius,
        strokeColor: "#FF0000",
        strokeOpacity: 1.0,
        strokeWeight: 5,
        fillColor: "#FF0000",
        fillOpacity: 0.25,
        map: map
    });
    
    map.setCenter(editcircle.getCenter());
    map.fitBounds(editcircle.getBounds());
}

function validate( name ) {
    
    if($.trim(name).length <= 0) {
        $('#errortext').html(messages.ERROR_1);
        $('#name').css('background', 'yellow');
        $('#name').focus();
        return false;
    } else {
        $('#name').css('background', 'white');
    }
    return true;
}
