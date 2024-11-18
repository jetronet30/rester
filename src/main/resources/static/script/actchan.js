
$(document).ready(function(){  
    $.ajax({  
        type: "POST",  
        url: '/activchannels/show_all',
        success: function(html){
            $("#tv_logo").html(html);
        }  
    });  
    return false;               
});
