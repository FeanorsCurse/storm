import groovy.util.XmlSlurper
import de.andreasschmitt.richui.taglib.renderer.*

class CheckedTreeViewTagLib {

    static namespace = "richui"

    Renderer checkedTreeViewRenderer

    def checkedTreeView = {attrs ->
        try {
            attrs.xml = new XmlSlurper().parseText(attrs.xml)
        }
        catch (Exception e) {
            log.error("Error parsing xml", e)
            return ""
        }
        
        //Render output
        try {
            out << checkedTreeViewRenderer.renderTag(attrs)
        }
        catch (RenderException e) {
            log.error(e)
        }
    }

}