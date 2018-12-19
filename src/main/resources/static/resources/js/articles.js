(function () {
    if($('.page-articles-ajax').length > 0){
        console.log("Yea Im working",$.get);
        $.get( "/articles/json", function( data ) {
            console.log('got data',data);
            if(data && data.length){
                for(var i = 0;i < data.length; i++){
                    var article = data[i];
                    console.log("article",article);
                }
            }
        });
    }

})();
