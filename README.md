**项目说明**

    recog项目是通过调用接口获取图片，实现图片上传接口以及调用ddl识别图片。
    一、JNA只支持32位的jdk，这是比较坑爹的地方
    二、dll文件的存放问题（也是巨坑）：
        网上有很多五花八门的说法，放在C:\Windows\System32下或放在jdk的bin目录下，其实没必要搞得那么乱七八糟的，
        就放在项目下面（可以自己指定路径），那么在获取指定的dll或其他文件可以使用下面的方法，其中Lib就是文件的所在文件夹中的一个类
        //绝对路径的地址获取，注意要去空格，特别坑，还要把把“/”转换成"\\"，replaceAll("/", "\\\\")
        Lib.class.getResource("").getPath().replaceFirst("/","").replace("target/classes", "src/main/java").replaceAll("/", "\\\\").replaceAll("%20"," ")+"文件名.dll";
    