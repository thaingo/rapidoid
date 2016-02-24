package org.rapidoid.var.impl;

/*
 * #%L
 * rapidoidcommons
 * %%
 * Copyright (C) 2014  2016 Nikolche Mihajlovski and contributors
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import org.rapidoid.annotation.Authors;
import org.rapidoid.annotation.Since;
import org.rapidoid.commons.Coll;
import org.rapidoid.u.U;
import org.rapidoid.var.Var;

import java.util.Set;

@Authors("Nikolche Mihajlovski")
@Since("2.0.0")
public abstract class AbstractVar<T> implements Var<T> {

	private static final long serialVersionUID = 6006051524799076017L;

	private final String name;

	private final Set<String> errors = Coll.synchronizedSet();

	public AbstractVar(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return U.str(get());
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public Set<String> errors() {
		return errors;
	}

	@Override
	public void set(T value) {
		try {
			doSet(value);
		} catch (Exception e) {
			error(e);
		}
	}

	protected abstract void doSet(T value);

	@Override
	public void error(Exception e) {
		if (e instanceof NumberFormatException) {
			errors().add("Invalid number!");
		} else {
			errors().add(U.or(e.getMessage(), "Invalid value!"));
		}
	}

}
