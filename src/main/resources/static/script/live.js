var video = document.getElementById("video");
        var hls = new Hls();
        activC();
        function activC(){
            hls.loadSource("https://tv.cdn.xsg.ge/gpb-1tv/index.m3u8")
            hls.attachMedia(video);
            video.play();
        }