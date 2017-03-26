/*
 * ao-net-types - Networking-related value types for Java.
 * Copyright (C) 2010-2013, 2016, 2017  AO Industries, Inc.
 *     support@aoindustries.com
 *     7262 Bull Pen Cir
 *     Mobile, AL 36695
 *
 * This file is part of ao-net-types.
 *
 * ao-net-types is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ao-net-types is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with ao-net-types.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aoindustries.net;

import com.aoindustries.dto.DtoFactory;
import com.aoindustries.io.FastExternalizable;
import com.aoindustries.io.FastObjectInput;
import com.aoindustries.io.FastObjectOutput;
import com.aoindustries.util.ComparatorUtils;
import com.aoindustries.util.Internable;
import com.aoindustries.validation.InvalidResult;
import com.aoindustries.validation.ValidResult;
import com.aoindustries.validation.ValidationException;
import com.aoindustries.validation.ValidationResult;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectInputValidation;
import java.io.ObjectOutput;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Represents a DNS domain label (a single part of a domain name between dots).  Domain labels must:
 * <ul>
 *   <li>Be non-null</li>
 *   <li>Be non-empty</li>
 *   <li>Conforms to definition in <a href="http://en.wikipedia.org/wiki/DNS_label#Parts_of_a_domain_name">http://en.wikipedia.org/wiki/DNS_label#Parts_of_a_domain_name</a></li>
 *   <li>Conforms to <a href="http://tools.ietf.org/html/rfc2181#section-11">RFC 2181</a></li>
 *   <li>And allow all numeric as described in <a href="http://tools.ietf.org/html/rfc1123#page-13">RFC 1123</a></li>
 * </ul>
 *
 * @author  AO Industries, Inc.
 */
final public class DomainLabel implements
	Comparable<DomainLabel>,
	FastExternalizable,
	ObjectInputValidation,
	DtoFactory<com.aoindustries.net.dto.DomainLabel>,
	Internable<DomainLabel>
{

	public static final int MAX_LENGTH = 63;

	/**
	 * Validates a domain name label.
	 */
	public static ValidationResult validate(String label) {
		if(label==null) return new InvalidResult(ApplicationResourcesAccessor.accessor, "DomainLabel.validate.isNull");
		return validate(label, 0, label.length());
	}
	public static ValidationResult validate(String label, int beginIndex, int endIndex) {
		if(label==null) return new InvalidResult(ApplicationResourcesAccessor.accessor, "DomainLabel.validate.isNull");
		int len = endIndex-beginIndex;
		if(len==0) return new InvalidResult(ApplicationResourcesAccessor.accessor, "DomainLabel.validate.empty");
		if(len>MAX_LENGTH) return new InvalidResult(ApplicationResourcesAccessor.accessor, "DomainLabel.validate.tooLong", MAX_LENGTH, len);
		for(int pos=beginIndex; pos<endIndex; pos++) {
			char ch = label.charAt(pos);
			if(ch=='-') {
				if(pos==beginIndex) return new InvalidResult(ApplicationResourcesAccessor.accessor, "DomainLabel.validate.startsDash");
				if(pos==(endIndex-1)) return new InvalidResult(ApplicationResourcesAccessor.accessor, "DomainLabel.validate.endsDash");
			} else if(
				(ch<'a' || ch>'z')
				&& (ch<'A' || ch>'Z')
				&& (ch<'0' || ch>'9')
			) return new InvalidResult(ApplicationResourcesAccessor.accessor, "DomainLabel.validate.invalidCharacter", ch, pos-beginIndex);
		}
		return ValidResult.getInstance();
	}

	private static final ConcurrentMap<String,DomainLabel> interned = new ConcurrentHashMap<String,DomainLabel>();

	/**
	 * @param label  when {@code null}, returns {@code null}
	 */
	public static DomainLabel valueOf(String label) throws ValidationException {
		if(label == null) return null;
		//DomainLabel existing = interned.get(label);
		//return existing!=null ? existing : new DomainLabel(label);
		return new DomainLabel(label);
	}

	private String label;
	private String lowerLabel;

	private DomainLabel(String label) throws ValidationException {
		this(label, label.toLowerCase(Locale.ROOT));
	}

	private DomainLabel(String label, String lowerLabel) throws ValidationException {
		this.label = label;
		this.lowerLabel = lowerLabel;
		validate();
	}

	private void validate() throws ValidationException {
		ValidationResult result = validate(label);
		if(!result.isValid()) throw new ValidationException(result);
	}

	@Override
	public boolean equals(Object O) {
		return
			O!=null
			&& O instanceof DomainLabel
			&& lowerLabel.equals(((DomainLabel)O).lowerLabel)
		;
	}

	@Override
	public int hashCode() {
		return lowerLabel.hashCode();
	}

	@Override
	public int compareTo(DomainLabel other) {
		return this==other ? 0 : ComparatorUtils.compareIgnoreCaseConsistentWithEquals(label, other.label);
	}

	@Override
	public String toString() {
		return label;
	}

	/**
	 * Gets the lower-case form of the label.  If two different domain labels are
	 * interned and their toLowerCase is the same String instance, then they are
	 * equal in case-insensitive manner.
	 */
	public String toLowerCase() {
		return lowerLabel;
	}

	/**
	 * Interns this label much in the same fashion as <code>String.intern()</code>.
	 *
	 * @see  String#intern()
	 */
	@Override
	public DomainLabel intern() {
		try {
			DomainLabel existing = interned.get(label);
			if(existing==null) {
				String internedLabel = label.intern();
				String internedLowerLabel = lowerLabel.intern();
				DomainLabel addMe = label==internedLabel && lowerLabel==internedLowerLabel ? this : new DomainLabel(internedLabel, lowerLabel);
				existing = interned.putIfAbsent(internedLabel, addMe);
				if(existing==null) existing = addMe;
			}
			return existing;
		} catch(ValidationException err) {
			// Should not fail validation since original object passed
			throw new AssertionError(err.getMessage());
		}
	}

	@Override
	public com.aoindustries.net.dto.DomainLabel getDto() {
		return new com.aoindustries.net.dto.DomainLabel(label);
	}

	// <editor-fold defaultstate="collapsed" desc="FastExternalizable">
	private static final long serialVersionUID = -3692661338685551188L;

	public DomainLabel() {
	}

	@Override
	public long getSerialVersionUID() {
		return serialVersionUID;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		FastObjectOutput fastOut = FastObjectOutput.wrap(out);
		try {
			fastOut.writeFastUTF(label);
		} finally {
			fastOut.unwrap();
		}
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		if(label!=null) throw new IllegalStateException();
		FastObjectInput fastIn = FastObjectInput.wrap(in);
		try {
			label = fastIn.readFastUTF();
			lowerLabel = label.toLowerCase(Locale.ROOT);
		} finally {
			fastIn.unwrap();
		}
	}

	@Override
	public void validateObject() throws InvalidObjectException {
		try {
			validate();
		} catch(ValidationException err) {
			InvalidObjectException newErr = new InvalidObjectException(err.getMessage());
			newErr.initCause(err);
			throw newErr;
		}
	}
	// </editor-fold>
}
