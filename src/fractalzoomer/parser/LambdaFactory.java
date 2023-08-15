package fractalzoomer.parser;

import java.lang.invoke.*;
import java.lang.reflect.Method;

public class LambdaFactory {

	public static <T> T create(MethodHandles.Lookup lookup, Method method, Class<T> interfaceClass, String signatatureName) throws Throwable {
		return create(lookup, method, interfaceClass, signatatureName, false);
	}

	private static <T> T create(MethodHandles.Lookup lookup, Method method, Class<T> interfaceClass, String signatureName, boolean invokeSpecial) throws Throwable {

		return createLambda(lookup, method, interfaceClass, signatureName, invokeSpecial);

	}
	public static <T> T createLambda(MethodHandles.Lookup lookup, Method method, Class<T> interfaceClass, String signatureName, boolean createSpecial) throws Throwable {

		MethodHandle methodHandle = createSpecial? lookup.unreflectSpecial(method, method.getDeclaringClass()) : lookup.unreflect(method);
		MethodType signature = methodHandle.type();

		CallSite site = LambdaMetafactory.metafactory(
				lookup,
				signatureName,
				MethodType.methodType(interfaceClass),
				signature,
				methodHandle,
				signature);

		MethodHandle factory = site.getTarget();
		return (T) factory.invoke();

	 }

}