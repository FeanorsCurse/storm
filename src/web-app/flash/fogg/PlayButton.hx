import flash.display.Graphics;

// PlayButton class

class PlayButton extends Button
{
    // Create a new PlayButton

    public function new()
    {
	super();
	draw();
    }

    // Draw the button

    override function draw()
    {
	// Array of pointers (sorry, not allowed to say that) to
	// ButtonState graphic objects

	var ag : Array<Graphics> = 
	    [cast(downState, ButtonState).graphics,
	     cast(hitTestState, ButtonState).graphics,
	     cast(overState, ButtonState).graphics,
	     cast(upState, ButtonState).graphics];

	// Draw the button shaded background

	super.draw();

	// Draw the button symbol on all four ButtonStates

	for (i in 0...ag.length)
	{
	    var g : Graphics = ag[i];

	    g.beginFill(0x404040);
	    g.moveTo(4, 4);
	    g.lineTo(17, 10);
	    g.lineTo(4, 17);
	    g.lineTo(4, 4);
	    g.endFill();
	}
    }
}
