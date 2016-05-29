# jaxtends
JAXB plugin for extending class for a specific element

Refer to the following sample xml snippet, to specify the class you wish to extend along with the package name. Not the first line, which is required to declare the namespace.

```xml

<xs:schema xmlns:ext="http://www.carbonrider.com/xjc-plugins/extends">
<xs:complextype name="MyElement">
	<xs:annotation>
		<xs:appinfo>
			<ext:extends package="com.mypackage.subpackage">MyElementBaseClass</ext:extends>
		</xs:appinfo>
	</xs:annotation>
</xs:complextype>
</xs:schema>

```

In addition to above you should also add an argument to XJC compiler as **-Xextends**