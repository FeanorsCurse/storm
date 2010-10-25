package de.andreasschmitt.richui.taglib.renderer

class AttributeTransformer {
	
	protected Map attributeTransformer = [:]
	
	protected void registerTransformer(String attributeName, Closure transformer){
		attributeTransformer[attributeName] = transformer
	}
	
	protected void unregisterTransformer(String attributeName){
		attributeTransformer.remove(attributeName)
	}
	
	protected String transform(String name, value){
		if(attributeTransformer.containsKey(name)){
			value = attributeTransformer[name].call(value)
		}
				
		return value
	}
	
	public static stringTransformer = { value ->
		if(!(value.startsWith("'") && value.endsWith("'"))){
			value = "'${value}'"
		}
				
		return value
	}
}