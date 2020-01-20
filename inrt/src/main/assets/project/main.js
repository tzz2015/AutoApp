console.show();
app.launchApp('微信');
sleep(2000);
mainEntrence();

//搜索新红包并点开领取
function searchNewRedpacket(){
    try {
    var rp_msg_list = className("android.widget.TextView").id('auk').find();
      log("调试信息" + rp_msg_list);
        if(rp_msg_list.length != 0){
            log("检测到的红包个数: " + rp_msg_list.length);
            for(var i = 0; i < rp_msg_list.length; i++){
                var rp_auk = rp_msg_list[i];
                var rp_auk_parent = rp_auk.parent();
                if(rp_auk_parent.childCount() == 1){
                    log("### 发现新红包");
                    var rpB = rp_auk_parent.bounds();
                    click(rpB.left, rpB.top, rpB.right, rpB.bottom);
                    log("成功打开红包消息");
                    openNewRedPacket();
                    sleep(1000);
                }else if(i == (rp_msg_list.length - 1)){
                    log("当前页面已检测完");
                    break;
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
//领取点开的红包
function openNewRedPacket(){
    try {
        var draw = desc("开").findOne(500);
        log("调试信息2");
        if(draw != null){
            log("#### 点开新红包");
            draw.click();
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
    while (true){
       sleep(200);
       try {
          searchNewRedpacket()
       }catch(error){
         log(error)
       }

    }
  
}