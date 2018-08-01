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

package personal.wuyi.jcsvorm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * CSV column annotation for mapping between the columns in CSV file and the 
 * fields in Java class
 * 
 * <p>Parameters:
 * <ul>
 * 	<li>name
 *  <li>pos
 *  <li>format
 *  <li>required
 * </ul>
 * 
 * @author  Wuyi Chen
 * @date    03/14/2017
 * @version 1.1
 * @since   1.1
 */
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CsvColumn {
	/**
	 * The column name in the CSV.
	 * 
	 * @since   1.1
	 */
	String  name()          default "";
	
	/**
	 * The index of the column. This index is 0-based.
	 * 
	 * @since   1.1
	 */
	int     pos()           default -Integer.MAX_VALUE;
	
	/**
	 * The format in the column, especially for date value. This parameter is 
	 * optional.
	 * 
	 * @since   1.1
	 */
	String  format()        default "";
	
	/**
	 * Whether the column is required in CSV file or not. When it is true but 
	 * the column is missing in a CSV file, the {@code CsvFactory} will throw an 
	 * exception. When it is false and the column is missing in a CSV file, 
	 * the {@code CsvFactory} will keep silence.
	 * 
	 * @since   1.1
	 */
	boolean required()      default false;
}
