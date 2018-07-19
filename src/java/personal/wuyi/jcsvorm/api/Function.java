package personal.wuyi.jcsvorm.api;

import java.text.ParseException;

/**
 * A one-argument function that takes the argument of type T and returns an R.
 * 
 * @author  Wuyi Chen
 * @date    03/24/2017
 * @version 1.1
 * @since   1.1
 *
 * @param <T>
 * @param <Boolean>
 */
public interface Function<T, R> {
	/**
	 * @param obj
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws ParseException 
	 */
	public R call(T f, Class<R> clazz) throws IllegalArgumentException, IllegalAccessException, ParseException;
}
