package com.github.startsmercury.noshades.util;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.function.Function;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Factory {
	public static interface Filter {
		<T, U> U of(T delegate, Function<T, U> constructor);
	}

	public static class SoftFilter implements Filter {
		private SoftReference delegate = NULL_SOFT_REFERENCE;

		private SoftReference filter = NULL_SOFT_REFERENCE;

		@Override
		public <T, U> U of(T delegate, Function<T, U> constructor) {
			if (this.delegate.get() != delegate) {
				this.delegate = asSoftReference(delegate);
				this.filter = asSoftReference(constructor.apply(delegate));
			}

			return (U) this.filter.get();
		}
	}

	public static class StrongFilter implements Filter {
		private Object delegate = null;

		private Object filter = null;

		@Override
		public <T, U> U of(T delegate, Function<T, U> constructor) {
			if (this.delegate != delegate) {
				this.delegate = delegate;
				this.filter = constructor.apply(delegate);
			}

			return (U) this.filter;
		}
	}

	public static class WeakFilter implements Filter {
		private WeakReference delegate = NULL_WEAK_REFERENCE;

		private WeakReference filter = NULL_WEAK_REFERENCE;

		@Override
		public <T, U> U of(T delegate, Function<T, U> constructor) {
			if (this.delegate.get() != delegate) {
				this.delegate = asWeakReference(delegate);
				this.filter = asWeakReference(constructor.apply(delegate));
			}

			return (U) this.filter.get();
		}
	}

	private static final SoftReference NULL_SOFT_REFERENCE = new SoftReference(null);

	private static final WeakReference NULL_WEAK_REFERENCE = new WeakReference(null);

	protected static SoftReference asSoftReference(Object x) {
		return x != null ? new SoftReference(x) : NULL_SOFT_REFERENCE;
	}

	protected static WeakReference asWeakReference(Object x) {
		return x != null ? new WeakReference(x) : NULL_WEAK_REFERENCE;
	}
}
