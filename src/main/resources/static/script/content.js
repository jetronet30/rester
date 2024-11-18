$(document).ready(function(){  
    $.ajax({  
        type: "POST",  
        url: '/content/show_all',
        success: function(html){
            $("#video_content").html(html);
        }  
    });  
    return false;               
});


                     
            
          
          