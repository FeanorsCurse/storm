function Slider() {

    // The slider can move 0 pixels up
    var topConstraint = 0;

    // The slider can move 200 pixels down
    var bottomConstraint = 200;

    // Custom scale factor for converting the pixel offset into a real value
    var scaleFactor = 0.5;

    // The amount the slider moves when the value is changed with the arrow
    // keys
    var keyIncrement = 20;
    
    // The amount the slider moves when the value is changed
    var tickSize = 1;
    
    var valueId;
    var inputId;
    
    this.setTopConstraint = function(topConstraint){
    	this.topConstraint = topConstraint;
    }
    
    this.setBottomConstraint = function(bottomConstraint){
    	this.bottomConstraint = bottomConstraint;
    }
    
    this.setScaleFactor = function(scaleFactor){
    	this.scaleFactor = scaleFactor;
    }
    
    this.setKeyIncrement = function(keyIncrement){
    	this.keyIncrement = keyIncrement;
    }
    
    this.setTickSize = function(tickSize){
    	this.tickSize = tickSize;
    }
    
    this.setValueId = function(valueId){
    	this.valueId = valueId;
    }
    
    this.setInputId = function(inputId){
    	this.inputId = inputId;
    }

	this.init = function() {
	    var Event = YAHOO.util.Event,
	        Dom   = YAHOO.util.Dom,
	        lang  = YAHOO.lang,
	        slider, 
	        bg="slider-bg-horizontal", thumb="slider-thumb", 
	        valuearea=this.valueId, textfield=this.inputId

    	Event.onDOMReady(function() {
	        slider = YAHOO.widget.Slider.getHorizSlider(bg, 
	                         thumb, topConstraint, bottomConstraint, tickSize);
	
	        slider.getRealValue = function() {
	            return Math.round(this.getValue() * scaleFactor);
	        }
	
	        slider.subscribe("change", function(offsetFromStart) {
	        	var valnode = Dom.get(valuearea);
	            var fld = Dom.get(textfield);
	
	            // Display the pixel value of the control
	            valnode.innerHTML = offsetFromStart;
	
	            // use the scale factor to convert the pixel offset into a real
	            // value
	            var actualValue = slider.getRealValue();
	
	            // update the text box with the actual value
	            fld.value = actualValue;
	
	            // Update the title attribute on the background.  This helps assistive
	            // technology to communicate the state change
	            Dom.get(bg).title = "slider value = " + actualValue;
        	});

       	 	slider.subscribe("slideStart", function() {
                YAHOO.log("slideStart fired", "warn");
            });

        	slider.subscribe("slideEnd", function() {
                YAHOO.log("slideEnd fired", "warn");
            });

	        // Listen for keystrokes on the form field that displays the
	        // control's value.  While not provided by default, having a
	        // form field with the slider is a good way to help keep your
	        // application accessible.
        	Event.on(textfield, "keydown", function(e) {
	            // set the value when the 'return' key is detected
	            if (Event.getCharCode(e) === 13) {
	                var v = parseFloat(this.value, 10);
	                v = (lang.isNumber(v)) ? v : 0;
	
	                // convert the real value into a pixel offset
	                slider.setValue(Math.round(v/scaleFactor));
	            }
	        });
        
	        // Use setValue to reset the value to white:
	        Event.on("putval", "click", function(e) {
	            slider.setValue(100, false); //false here means to animate if possible
	        });
        
	        // Use the "get" method to get the current offset from the slider's start
	        // position in pixels.  By applying the scale factor, we can translate this
	        // into a "real value
	        Event.on("getval", "click", function(e) {
	            YAHOO.log("Current value: "   + slider.getValue() + "\n" + 
	                      "Converted value: " + slider.getRealValue(), "info", "example"); 
	        });
    	});
	}
}
