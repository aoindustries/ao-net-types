/*
 * ao-net-types - Networking-related value types.
 * Copyright (C) 2019  AO Industries, Inc.
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

import com.aoindustries.io.Encoder;
import com.aoindustries.util.StringUtility;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

/**
 * URI decoding utilities.
 * <p>
 * TODO: These methods are for highest performance and are consistent with the JavaScript methods.
 * They are not meant for general purpose URL manipulation, and are not trying to replace
 * any full-featured URI tools.
 * <p>
 * Consider the following if needing more than what this provides (in no particular order):
 * </p>
 * <ol>
 * <li>{@link URL}</li>
 * <li>{@link java.net.URI}</li>
 * <li><a href="https://hc.apache.org/httpcomponents-client-ga/httpclient/apidocs/org/apache/http/client/utils/URIBuilder.html">URIBuilder</a></li>
 * <li><a href="https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/util/UriUtils.html">UriUtils</a></li>
 * <li><a href="https://guava.dev/releases/19.0/api/docs/com/google/common/net/UrlEscapers.html">UrlEscapers</a></li>
 * <li><a href="https://jena.apache.org/documentation/notes/iri.html">jena-iri</a></li>
 * <li><a href="https://github.com/xbib/net>org.xbib:net-url</a></li>
 * </ol>
 *
 * @see  URLDecoder
 *
 * @author  AO Industries, Inc.
 */
public class URIDecoder {

	private URIDecoder() {}

	/**
	 * Decodes a value from its use in a path component or fragment in the default encoding <code>{@link IRI#ENCODING}</code>.
	 * <p>
	 * This uses {@link URLDecoder#decode(java.lang.String, java.lang.String)}.
	 * </p>
	 * <p>
	 * See <a href="https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/decodeURIComponent">decodeURIComponent() - JavaScript | MDN</a>
	 * </p>
	 *
	 * @see URIEncoder#encodeURIComponent(java.lang.String)
	 */
	public static String decodeURIComponent(String s) {
		try {
			return (s == null) ? null : URLDecoder.decode(s, IRI.ENCODING.name());
		} catch(UnsupportedEncodingException e) {
			throw new AssertionError("Standard encoding (" + IRI.ENCODING + ") should always exist", e);
		}
	}

	/**
	 * Decodes a value from its use in a path component or fragment in the default encoding <code>{@link IRI#ENCODING}</code>.
	 * <p>
	 * This uses {@link URLDecoder#decode(java.lang.String, java.lang.String)}.
	 * </p>
	 * <p>
	 * See <a href="https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/decodeURIComponent">decodeURIComponent() - JavaScript | MDN</a>
	 * </p>
	 *
	 * @see URIEncoder#encodeURIComponent(java.lang.String, java.lang.Appendable)
	 */
	public static void decodeURIComponent(String s, Appendable out) throws IOException {
		try {
			if(s != null) out.append(URLDecoder.decode(s, IRI.ENCODING.name()));
		} catch(UnsupportedEncodingException e) {
			throw new AssertionError("Standard encoding (" + IRI.ENCODING + ") should always exist", e);
		}
	}

	/**
	 * Decodes a value from its use in a path component or fragment in the default encoding <code>{@link IRI#ENCODING}</code>.
	 * <p>
	 * This uses {@link URLDecoder#decode(java.lang.String, java.lang.String)}.
	 * </p>
	 * <p>
	 * See <a href="https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/decodeURIComponent">decodeURIComponent() - JavaScript | MDN</a>
	 * </p>
	 *
	 * @param encoder  An optional encoder the output is applied through
	 *
	 * @see URIEncoder#encodeURIComponent(java.lang.String, java.lang.Appendable, com.aoindustries.io.Encoder)
	 */
	public static void decodeURIComponent(String s, Appendable out, Encoder encoder) throws IOException {
		try {
			if(s != null) {
				if(encoder == null) {
					decodeURIComponent(s, out);
				} else {
					encoder.append(URLDecoder.decode(s, IRI.ENCODING.name()), out);
				}
			}
		} catch(UnsupportedEncodingException e) {
			throw new AssertionError("Standard encoding (" + IRI.ENCODING + ") should always exist", e);
		}
	}

	/**
	 * Decodes a value from its use in a path component or fragment in the default encoding <code>{@link IRI#ENCODING}</code>.
	 * <p>
	 * This uses {@link URLDecoder#decode(java.lang.String, java.lang.String)}.
	 * </p>
	 * <p>
	 * See <a href="https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/decodeURIComponent">decodeURIComponent() - JavaScript | MDN</a>
	 * </p>
	 *
	 * @see URIEncoder#encodeURIComponent(java.lang.String, java.lang.StringBuilder)
	 */
	public static void decodeURIComponent(String s, StringBuilder sb) {
		try {
			decodeURIComponent(s, (Appendable)sb);
		} catch(IOException e) {
			throw new AssertionError("IOException should not occur on StringBuilder", e);
		}
	}

	/**
	 * Decodes a value from its use in a path component or fragment in the default encoding <code>{@link IRI#ENCODING}</code>.
	 * <p>
	 * This uses {@link URLDecoder#decode(java.lang.String, java.lang.String)}.
	 * </p>
	 * <p>
	 * See <a href="https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/decodeURIComponent">decodeURIComponent() - JavaScript | MDN</a>
	 * </p>
	 *
	 * @see URIEncoder#encodeURIComponent(java.lang.String, java.lang.StringBuffer)
	 */
	public static void decodeURIComponent(String s, StringBuffer sb) {
		try {
			decodeURIComponent(s, (Appendable)sb);
		} catch(IOException e) {
			throw new AssertionError("IOException should not occur on StringBuffer", e);
		}
	}

	/**
	 * Percent-encodes reserved characters (and '%' for already percent-encoded) only.
	 *
	 * @param encoder  An optional encoder the output is applied through
	 */
	// TODO: Implement as streaming encoder
	private static void encodeRfc3968ReservedCharacters_and_percent(String value, Appendable out, Encoder encoder) throws IOException {
		int len = value.length();
		for(int i = 0; i < len; i++) {
			char ch = value.charAt(i);
			String replacement;
			switch(ch) {
				// gen-delims
				case ':' :
					replacement = "%3A";
					break;
				case '/' :
					replacement = "%2F";
					break;
				case '?' :
					replacement = "%3F";
					break;
				case '#' :
					replacement = "%23";
					break;
				case '[' :
					replacement = "%5B";
					break;
				case ']' :
					replacement = "%5D";
					break;
				case '@' :
					replacement = "%40";
					break;
				// sub-delims
				case '!' :
					replacement = "%21";
					break;
				case '$' :
					replacement = "%24";
					break;
				case '&' :
					replacement = "%26";
					break;
				case '\'' :
					replacement = "%27";
					break;
				case '(' :
					replacement = "%28";
					break;
				case ')' :
					replacement = "%29";
					break;
				case '*' :
					replacement = "%2A";
					break;
				case '+' :
					replacement = "%2B";
					break;
				case ',' :
					replacement = "%2C";
					break;
				case ';' :
					replacement = "%3B";
					break;
				case '=' :
					replacement = "%3D";
					break;
				// already percent-encoded
				case '%' :
					replacement = "%25";
					break;
				default :
					replacement = null;
			}
			if(replacement != null) {
				if(encoder == null) {
					out.append(replacement);
				} else {
					encoder.append(replacement, out);
				}
			} else {
				if(encoder == null) {
					out.append(ch);
				} else {
					encoder.append(ch, out);
				}
			}
		}
	}

	/**
	 * Decodes a URI to <a href="https://tools.ietf.org/html/rfc3987">RFC 3987 Unicode format</a> in the default encoding <code>{@link IRI#ENCODING}</code>.
	 * Decodes the characters in the URI, not including any characters defined in
	 * <a href="https://tools.ietf.org/html/rfc3986#section-2.2">RFC 3986: Reserved Characters</a>.
	 * Furthermore, characters that would decode to a reserved character are left percent-encoded to avoid ambiguity.
	 * <p>
	 * See <a href="https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/decodeURI">decodeURI() - JavaScript | MDN</a>
	 * </p>
	 *
	 * @return  The decoded URI or {@code url} when not modified
	 *
	 * @see URIEncoder#encodeURI(java.lang.String)
	 */
	public static String decodeURI(String uri) {
		if(uri == null) return null;
		StringBuilder sb = new StringBuilder(uri.length());
		decodeURI(uri, sb);
		if(sb.length() == uri.length()) {
			assert uri.equals(sb.toString());
			return uri;
		} else {
			return sb.toString();
		}
	}

	/**
	 * Decodes a URI to <a href="https://tools.ietf.org/html/rfc3987">RFC 3987 Unicode format</a> in the default encoding <code>{@link IRI#ENCODING}</code>.
	 * Decodes the characters in the URI, not including any characters defined in
	 * <a href="https://tools.ietf.org/html/rfc3986#section-2.2">RFC 3986: Reserved Characters</a>.
	 * Furthermore, characters that would decode to a reserved character are left percent-encoded to avoid ambiguity.
	 * <p>
	 * See <a href="https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/decodeURI">decodeURI() - JavaScript | MDN</a>
	 * </p>
	 *
	 * @see URIEncoder#encodeURI(java.lang.String, java.lang.Appendable)
	 */
	public static void decodeURI(String uri, Appendable out) throws IOException {
		decodeURI(uri, out, null);
	}

	/**
	 * Decodes a URI to <a href="https://tools.ietf.org/html/rfc3987">RFC 3987 Unicode format</a> in the default encoding <code>{@link IRI#ENCODING}</code>.
	 * Decodes the characters in the URI, not including any characters defined in
	 * <a href="https://tools.ietf.org/html/rfc3986#section-2.2">RFC 3986: Reserved Characters</a>.
	 * Furthermore, characters that would decode to a reserved character are left percent-encoded to avoid ambiguity.
	 * <p>
	 * See <a href="https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/decodeURI">decodeURI() - JavaScript | MDN</a>
	 * </p>
	 *
	 * @param encoder  An optional encoder the output is applied through
	 *
	 * @see URIEncoder#encodeURI(java.lang.String, java.lang.Appendable, com.aoindustries.io.Encoder)
	 */
	public static void decodeURI(String uri, Appendable out, Encoder encoder) throws IOException {
		if(uri != null) {
			int len = uri.length();
			int pos = 0;
			while(pos < len) {
				int nextPos = StringUtility.indexOf(uri, RFC3986.RESERVED, pos);
				if(nextPos == -1) {
					// TODO: A specialized form of decode that skips decoding to reserved characters would be better than decode/re-encode.
					//       This implementation is less precise, such as converting lower-case percent-encoded to upper-case.
					// TODO: Avoid substring?
					encodeRfc3968ReservedCharacters_and_percent(decodeURIComponent(uri.substring(pos)), out, encoder);
					pos = len;
				} else {
					if(nextPos != pos) {
						// TODO: A specialized form of decode that skips decoding to reserved characters would be better than decode/re-encode.
						//       This implementation is less precise, such as converting lower-case percent-encoded to upper-case.
						// TODO: Avoid substring?
						encodeRfc3968ReservedCharacters_and_percent(decodeURIComponent(uri.substring(pos, nextPos)), out, encoder);
					}
					char reserved = uri.charAt(nextPos++);
					if(encoder == null) {
						out.append(reserved);
					} else {
						encoder.append(reserved, out);
					}
					pos = nextPos;
				}
			}
		}
	}

	/**
	 * Decodes a URI to <a href="https://tools.ietf.org/html/rfc3987">RFC 3987 Unicode format</a> in the default encoding <code>{@link IRI#ENCODING}</code>.
	 * Decodes the characters in the URI, not including any characters defined in
	 * <a href="https://tools.ietf.org/html/rfc3986#section-2.2">RFC 3986: Reserved Characters</a>.
	 * Furthermore, characters that would decode to a reserved character are left percent-encoded to avoid ambiguity.
	 * <p>
	 * See <a href="https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/decodeURI">decodeURI() - JavaScript | MDN</a>
	 * </p>
	 *
	 * @see URIEncoder#encodeURI(java.lang.String, java.lang.StringBuilder)
	 */
	public static void decodeURI(String uri, StringBuilder sb) {
		try {
			decodeURI(uri, sb, null);
		} catch(IOException e) {
			throw new AssertionError("IOException should not occur on StringBuilder", e);
		}
	}

	/**
	 * Decodes a URI to <a href="https://tools.ietf.org/html/rfc3987">RFC 3987 Unicode format</a> in the default encoding <code>{@link IRI#ENCODING}</code>.
	 * Decodes the characters in the URI, not including any characters defined in
	 * <a href="https://tools.ietf.org/html/rfc3986#section-2.2">RFC 3986: Reserved Characters</a>.
	 * Furthermore, characters that would decode to a reserved character are left percent-encoded to avoid ambiguity.
	 * <p>
	 * See <a href="https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/decodeURI">decodeURI() - JavaScript | MDN</a>
	 * </p>
	 *
	 * @see URIEncoder#encodeURI(java.lang.String, java.lang.StringBuffer)
	 */
	public static void decodeURI(String uri, StringBuffer sb) {
		try {
			decodeURI(uri, sb, null);
		} catch(IOException e) {
			throw new AssertionError("IOException should not occur on StringBuffer", e);
		}
	}
}
