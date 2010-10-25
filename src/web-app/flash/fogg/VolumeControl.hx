import flash.geom.Matrix;
import flash.display.Sprite;
import flash.display.Graphics;
import flash.events.MouseEvent;
import flash.display.GradientType;
import flash.display.SpreadMethod;
import flash.display.InterpolationMethod;

// Class VolumeControl

class VolumeControl extends Sprite
{
    var volume : Float;

    // Create a new VolumeControl

    public function new()
    {
	super();

	// Add event listener for click event

	addEventListener(MouseEvent.CLICK, click);
	volume = 1.0;
	draw(graphics);
    }

    // Draw the volume control

    function draw(g : Graphics)
    {
	// Create a nice gradient

	var colors : Array<Int> = [0xffffff, 0xd0d0d0];
	var alphas : Array<Int> = [1, 1];
	var ratios : Array<Int> = [0, 255];
	var matrix : Matrix = new Matrix();

	matrix.createGradientBox(25, 13, Math.PI/2, 0, 0);
	g.beginGradientFill(GradientType.LINEAR, 
			    colors,
			    alphas,
			    ratios, 
			    matrix, 
			    SpreadMethod.PAD, 
			    InterpolationMethod.LINEAR_RGB, 
			    0);

	// Draw the background

	g.drawRoundRect(0, 0, 27, 15, 5, 5);
	g.endFill();

	// Draw vertical bars coloured according to the volume setting

	g.beginFill(0x303030);
	for (i in 0...Math.round(volume * 13))
	    g.drawRect(1 + (i * 2), 13 - i, 1, 1 + i);

	g.endFill();
	g.beginFill(0xc0c0c0);
	for (i in Math.round(volume * 13)...13)
	    g.drawRect(1 + (i * 2), 13 - i, 1, 1 + i);

	g.endFill();
    }

    // Click event

    function click(e)
    {
	// Calculate the volume from the x offset

	volume = e.localX / (width - 2);

	if (volume > 1)
	    volume = 1;

	// Draw the new state

	draw(graphics);

	// Dispatch an event if there's a listener

	if (hasEventListener(ValueChangeEvent.VALUE_CHANGE))
	    dispatchEvent(new ValueChangeEvent(volume));
    }
}
