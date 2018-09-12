/**
 * Created by damen on 2014/10/14.
 */
var protops = {}
protops.base = {

    toggleHide : function(el){
        el.toggleClass("hide");
    },

    hint : function(msg){
        $("#err-hint").text(msg).show();
        setTimeout(function(){
            $("#err-hint").hide();
        },3000);
    },
    baseSend : function(url, params, successCallback){

        var data = {};
        data.params = params;

        $.ajax({
            type: 'POST',
            url: url,
            data: JSON.stringify(data),
            contentType: 'application/json;charset=utf-8',
            timeout: 20000,
            success: function(response){
                successCallback(response);

            },
            error: function(xhr, type){

            }
        })
    },

    send : function(url, params, successCallback){

        var data = {};
        data.params = params;

        $.ajax({
            type: 'POST',
            url: url,
            data: JSON.stringify(data),
            contentType: 'application/json;charset=utf-8',
            timeout: 40000,
            success: function(response){
                if(response.respCode != "00"){
                    protops.base.hint(response.msg);
                    return false;
                }
                successCallback(response.params);

            },
            error: function(xhr, type){
            }
        })
    },

    startLoading : function(){
        $(".loading-block").removeClass("hide");
    },

    endLoading : function(){
        $(".loading-block").addClass("hide");
    }


}