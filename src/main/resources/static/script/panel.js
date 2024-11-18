
setInterval(post_load, 5000)

function post_load(){
    $.ajax({
        url: "/panel/post_load",
        type: "POST",
        contentType: "application/json;",
        dataType: "json",
        data: {},
        success: function(data) {
            $("#load_cpu").html(data.cpu);
            $("#total_memory").html(data.total);
            $("#memory").html(data.mem);
            $("#total_space").html(data.total_space);
            $("#free_space").html(data.free_space);
            $("#connect_user").html(data.users);
            $("#activ_stream").html(data.activstream);
        },
       
    });
}

$("#reload").click(function(e) {
    e.preventDefault();
    $.ajax({
        type: "GET",
        url: "/panel/reload",
        success: function(result) {
            alert('service is restart');
        },
        error: function(result) {
            alert('error');
        }
    });
});

$("#shut_down").click(function(e) {
    e.preventDefault();
    $.ajax({
        type: "GET",
        url: "/panel/shutdown",
        success: function(result) {
            alert('service is shutdown');
        },
        error: function(result) {
            alert('error');
        }
    });
});






