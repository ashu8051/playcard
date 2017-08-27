//
$(document).ready(function() {
  
	  
    $("#btnla").click(function() {
    	 $("select.sellft");
    	 $("#assetID option").attr('selected','selected'); 
        var left = $("select.sellft   option");
        var right = $("select.selrht");
        right.append(left);
    });
    
    
    $("#btnlo").click(function() {
        var left = $("select.sellft option:selected");
        var right = $("select.selrht");
        right.append(left);
    });
    $("#btnro").click(function() {
        var left = $("select.sellft");
        var right = $("select.selrht option:selected");
        left.append(right);
    });
    $("#btnra").click(function() {
        var left = $("select.sellft");
        var right = $("select.selrht option");
        left.append(right);
    });
    
    $("select.sellft").dblclick(function() {
        var left = $("select.sellft option:selected");
        var right = $("select.selrht");
        right.append(left);
    });
    $("select.selrht").dblclick(function() {
        var left = $("select.sellft");
        var right = $("select.selrht option:selected");
        left.append(right);
    });
    
    $("table.gridtbl tbody tr:odd > td").css("background-color", "#FFFFFF" );
   
    
    $("table.gridtbl tbody tr td").mouseover(function() {
        $(this).addClass("gridtblhover");
    });
    
    $("table.gridtbl tbody tr").mouseover(function() {
        $(this).children().removeClass("gridtblhover");
    });
    
    $('#Image').mouseover(function()
    		{
    		   $(this).css("cursor","pointer");
    		   $(this).animate({width: "500px"}, 'slow');
    		});
    
});
$.fn.sortSelect = function() {
    var op = this.children("option");
    op.sort(function(a, b) {
        return a.text.toLowerCase() > b.text.toLowerCase() ? 1 : -1;
    });
    return this.empty().append(op);
};
function IsValidEmails(emails) {
    emailarr = $.trim(emails).split(',');
    for(var i=0; i<emailarr.length; i++) {
        var emailele = $.trim(emailarr[i]);
        if(!IsValidEmail(emailele)) {
            return false;
        }
    }
    return true;
}
function IsValidEmail(email) {
    var filter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
    return filter.test(email);
}
function IsValidMobileNumbers(mobilenumbers) {
    mobilenumberarr = $.trim(mobilenumbers).split(',');
    for(var i=0; i<mobilenumberarr.length; i++) {
        var mobilenumberele = $.trim(mobilenumberarr[i]);
        if(!IsValidMobileNumber(mobilenumberele)) {
            return false;
        }
    }
    return true;
}
function IsValidMobileNumber(mobilenumber) {
    var filter = /^[0-9]{10}$/;
    return filter.test(mobilenumber);
}