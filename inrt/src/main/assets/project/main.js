console.show();
app.launchApp('微信');
sleep(2000);
mainEntrence();

//搜索新红包并点开领取
function searchNewRedpacket(){
    try {
    var rp_msg_list = className("android.widget.TextView").id("y0").find();
//    var rp_msg_list = className("android.widget.TextView").text("恭喜发财，大吉大利").find();
        if(rp_msg_list.length != 0){
            log("检测到的红包个数: " + rp_msg_list.length);
            for(var i = rp_msg_list.length-1; i >= 0; i--){
                var rp_auk = rp_msg_list[i];
                var rp_auk_parent = rp_auk.parent();
                if(rp_auk_parent.childCount() == 1){
                    log("### 发现新红包");
                    var rpB = rp_auk_parent.bounds();
                    click(rpB.left, rpB.top, rpB.right, rpB.bottom);
                    log("成功打开红包消息");
                    openNewRedPacket();
                    sleep(1000);
                }else{
                    log("无效红包, 跳过");
                }
            }
        }else if(rp_msg_list.empty()){
            log("未检测到红包消息" + rp_msg_list.length);
        }else{
            return;
        }
     }catch(error){
        log(error)
      }
}

function searchNewRedpacket2(){
     var btnSnapUp = className("android.widget.TextView").id("ape").findOne()
//     var btnSnapUp = className("android.widget.TextView").text("恭喜发财，大吉大利").findOne()
     if(btnSnapUp != null){
       log("### 发现新红包");
       var rpB = btnSnapUp.bounds();
       click(rpB.left, rpB.top, rpB.right, rpB.bottom);
       log("成功打开红包消息");
        var kai = className("android.widget.ImageButton").id('gir').findOne(1000);
        if(kai != null){
         var kaiBound = kai.bounds();
         click(kaiBound.left, kaiBound.top, kaiBound.right, kaiBound.bottom);
          log("点击打开")
        }else{
          back();
        }
        sleep(500);
     }
}

//领取点开的红包
function openNewRedPacket(){
    try {
        var draw = className("android.widget.ImageButton").id('gir').findOne(1000);
        if(draw != null){
            log("#### 点开新红包");
            var draw = draw.bounds();
            click(draw.left, draw.top, draw.right, draw.bottom);
            log("#### Gain a LUCKY succesfully!!!");
            sleep(500);
            //领完返回聊天主页
            back();
        }else{
            log("过期之类无效红包");
        }
        back();
        log("返回成功");
    }catch(error){
      log(error)
    }
}

//程序主入口
function mainEntrence(){

     var isRunNotification=false
    while (true){
       sleep(1000);
       try {
          searchNewRedpacket()
//           searchNewRedpacket2()
       }catch(error){
         log(error)
       }

    }


}