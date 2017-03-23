/*
 * ao-net-types - Networking-related value types for Java.
 * Copyright (C) 2017  AO Industries, Inc.
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

/**
 * The supported address families, such as IPv4 and IPv6.
 * <p>
 * Java 1.7+: Prefer <a href="https://docs.oracle.com/javase/7/docs/api/java/net/StandardProtocolFamily.html">StandardProtocolFamily</a>.
 * </p>
 *
 * @author  AO Industries, Inc.
 */
public enum AddressFamily {

	/**
	 * Internet Protocol Version 4 (IPv4).
	 */
	INET,

	/**
	 * Internet Protocol Version 6 (IPv6).
	 */
	INET6;
}