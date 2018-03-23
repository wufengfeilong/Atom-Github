(function() {
	/*Œš—§–Í?y?Û*/
	var modalBox = {};
	/*?æ–Í?y*/
	modalBox.modal = document.getElementById("myModal");
    /*?“¾triggerˆÂ?*/
	modalBox.triggerBtn = document.getElementById("triggerBtn");
    /*?“¾??ˆÂ?*/
	modalBox.closeBtn = document.getElementById("closeBtn");
	/*–Í?y?¦*/
	modalBox.show = function() {
		console.log(this.modal);
		this.modal.style.display = "block";
	}
	/*–Í?y??*/
	modalBox.close = function() {
		this.modal.style.display = "none";
	}
	/*“–—p?“_?–Í?y“à—e”VŠO“I‹æˆæC–Í?y–ç‰ï??*/
	modalBox.outsideClick = function() {
		var modal = this.modal;
		window.onclick = function(event) {
            if(event.target == modal) {
            	modal.style.display = "none";
            }
		}
	}
    /*–Í?y‰n‰»*/
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