"use strict";

$(document).on('ready', function(e) {

    var dialog = document.querySelector('dialog');
    var $to =   $('#dialog-message-to');
    var $peoples = $('#dialog-message-peoples');
    var $message = $('#dialog-message');
    var $currentSendTo = null;
    if (! dialog.showModal) {
      dialogPolyfill.registerDialog(dialog);
    }

    $('.show-dialog').on('click', function(e) {
      var $this = $(this);
      $to.html($this.data("to"));
      $peoples.html($this.data("peoples"));
      $message.html('');
      $currentSendTo = $this;
      dialog.showModal();
    });
    $('.ct-dialog-close').on('click', function(e) {
      dialog.close();
    });
    $('.ct-dialog-send').on('click', function(e) {
      $.ajax({
        type:"POST",
        url: "http://localhost:3000/api/message/send/to/"+$currentSendTo.data("id"),
        data:{message: $message.html()},
        success:function(){
          alert($message.data("success"));
          dialog.close();
        },
        error:function(e){
          console.log(e);
          alert($message.data("error"));
        }
      })
    });

});
