/*
 * Copyright 2018 Wuyi Chen.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
