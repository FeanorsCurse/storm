import flash.text.TextField;
import flash.text.TextFormat;

// TitleText class

class TitleText extends TextField
{
    // Create a new TitleText

    public function new()
    {
	super();

	// Set the dimensions

	height = 15;
	width = 250;
		
	selectable = false;

	// Create a TextFormat for the embedded font

	var format : TextFormat = new TextFormat();

	format.font = "Silkscreen";
	format.size = 12;
		
	embedFonts = true;
	defaultTextFormat = format;
    }
}
