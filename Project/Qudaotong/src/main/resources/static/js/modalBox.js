(function() {
	/*������?�y?��*/
	var modalBox = {};
	/*?���?�y*/
	modalBox.modal = document.getElementById("myModal");
    /*?��trigger��?*/
	modalBox.triggerBtn = document.getElementById("triggerBtn");
    /*?��??��?*/
	modalBox.closeBtn = document.getElementById("closeBtn");
	/*��?�y?��*/
	modalBox.show = function() {
		console.log(this.modal);
		this.modal.style.display = "block";
	}
	/*��?�y??*/
	modalBox.close = function() {
		this.modal.style.display = "none";
	}
	/*���p?�_?��?�y���e�V�O�I���C��?�y���??*/
	modalBox.outsideClick = function() {
		var modal = this.modal;
		window.onclick = function(event) {
            if(event.target == modal) {
            	modal.style.display = "none";
            }
		}
	}
    /*��?�y���n��*/
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