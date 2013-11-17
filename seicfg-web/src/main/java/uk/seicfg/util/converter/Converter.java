package uk.seicfg.util.converter;

public interface Converter<From, To> {
	
	To convertFrom(From from);
	
	From convertTo(To to);
	
}
