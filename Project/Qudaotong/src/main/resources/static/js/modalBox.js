(function() {
	/*§Ν?y?Ϋ*/
	var modalBox = {};
	/*?ζΝ?y*/
	modalBox.modal = document.getElementById("myModal");
    /*?ΎtriggerΒ?*/
	modalBox.triggerBtn = document.getElementById("triggerBtn");
    /*?Ύ??Β?*/
	modalBox.closeBtn = document.getElementById("closeBtn");
	/*Ν?y?¦*/
	modalBox.show = function() {
		console.log(this.modal);
		this.modal.style.display = "block";
	}
	/*Ν?y??*/
	modalBox.close = function() {
		this.modal.style.display = "none";
	}
	/*p?_?Ν?yΰeVOIζζCΝ?yηο??*/
	modalBox.outsideClick = function() {
		var modal = this.modal;
		window.onclick = function(event) {
            if(event.target == modal) {
            	modal.style.display = "none";
            }
		}
	}
    /*Ν?yn»*/
	modalBox.init = function() {
		var that = this;
		this.triggerBtn.onclick = function() {
            that.show();
		}
		this.closeBtn.onclick = function() {
			that.close();
		}
		this.outsideClick();
	}
	modalBox.init();

})();