StringBuilder sb = new StringBuilder()
String environmentVariable = System.getenv("JAVA_HOME");  //[1]
sb.append("[2] environmentVariable= " + environmentVariable + "\n");

Map<String, String> environmentVariables = System.getenv();  //[3]
for(Map.Entry<String, String> entry : environmentVariables.entrySet()) {  //[4]
	sb.append("[5]" + entry.getKey() + " = " + entry.getValue() + "\n");
}

sb.append("[6] JAVA_HOME = " + environmentVariables.get("JAVA_HOME") + "\n");

System.out.println(sb.toString())