package com.changgyu.my_processor

import com.changgyu.my_annotation.MyAnnotation
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.metadata.KotlinPoetMetadataPreview
import com.squareup.kotlinpoet.metadata.specs.internal.ClassInspectorUtil
import com.squareup.kotlinpoet.metadata.toImmutableKmClass
import java.io.File
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

@KotlinPoetMetadataPreview  //KotlinPoet을 사용하기 위한 어노테이션 정의
@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("com.changgyu.my_annotation.MyAnnotation")    // 이 프로세서가 어떤 어노테이션을 지원하는지 정의
class MyProcessor: AbstractProcessor() {

    lateinit var testFileBuilder: FileSpec

    /*
    * Annotation Processor은 컴파일 타임에 처리되기 때문에
    * Run Console에서 print를 찍어도 안보인다.
    * Build창으로 가서 찍히는 로그를 봐야 한다.
    * 또한 컴파일 타임에 로그를 찍기 위해선 println이나 Log 클래스가 아니라
    * Messager클래스의 processingEnv.messager.printMessage(Diagnostic.Kind.~~, "")
    * 를 사용해야 한다.
    *
    * */

    override fun process(
            annotations: MutableSet<out TypeElement>?,  //이 프로세서가 처리하는 어노테이션들에 대한 TypeElement
            roundEnv: RoundEnvironment      //어노테이션에 붙은 Elements 를 갖는 인터페이스
    ): Boolean {
        if (!roundEnv.errorRaised() && !roundEnv.processingOver()) {    //이전 라운드에서 에러가 안나고 다음 라운드의 대상이 아닐때

            val annotatedClasses = roundEnv.getElementsAnnotatedWith(MyAnnotation::class.java)
            if(annotatedClasses.isEmpty()) return false

            annotatedClasses.forEach {
                if(it.kind != ElementKind.CLASS) return false
                makeMyInfoPrintFunc(it)
                makeMyInformationClass(it)
            }
        }

        return false
    }

    private fun makeMyInfoPrintFunc(element: Element){
        val typeMetadata = element.getAnnotation(Metadata::class.java)
        val kmClass = typeMetadata.toImmutableKmClass()	 //metadata 클래스 생성
        val className = ClassInspectorUtil.createClassName(kmClass.name)	 //createClassName 생성
        val printInfoFunSpec = FunSpec.builder("printInfo").receiver(className)

        val myAnnotation = element.getAnnotation(MyAnnotation::class.java)
        myAnnotation.run {
            if(this.age>100){
                processingEnv.messager.printMessage(Diagnostic.Kind.ERROR, "age cannot be over 100", element)
            }
            printInfoFunSpec.addStatement("println(%P)", "name : ${this.name}, age : ${this.age}, job : ${this.job}")
        }
        testFileBuilder = FileSpec.builder("","${element.simpleName}_MyInfoPrintFunc").addFunction(printInfoFunSpec.build()).build()
        val kaptKotlinGeneratedDir = processingEnv.options["kapt.kotlin.generated"]!!
        testFileBuilder.writeTo(File(kaptKotlinGeneratedDir))

    }

    private fun makeMyInformationClass(element: Element){
        testFileBuilder = FileSpec.builder("","${element.simpleName}_MyInformation") // 파일이름 설정
                .addType(TypeSpec.classBuilder("${element.simpleName}_MyInformation") //클래스 이름 설정
                        .primaryConstructor(FunSpec.constructorBuilder()    //클래스 생성자 설정
                                .addParameter("name", String::class, KModifier.PRIVATE) //생성자에 name Parameter 추가
                                .addParameter("age", Int::class, KModifier.PRIVATE) //생성자에 age Parameter 추가
                                .build())                               //클래스 생성자 설정 완료
                        .addProperty(PropertySpec.builder("name", String::class)  // 클래스에 name Property 추가
                                .initializer("name")                                    //초기값 설정(생성자로 받은 name 설정)
                                .build())
                        .addProperty(PropertySpec.builder("age", Int::class)    // 클래스에 age Property 추가
                                .initializer("age")                                   //초기값 설정(생성자로 받은 age 설정)
                                .build())
                        .addProperty(PropertySpec.builder("job", String::class) // 클래스에 job Property 추가
                                .initializer("\"programmer\"")                        //초기값 설정(job = programmer)
                                .build())
                        .addFunction(FunSpec.builder("printMyInfo")             // 클래스에 printMyInfo 함수 추가
                                .addStatement("println(%P)", "name : \$name, age : \$age, job : \$job")        // print 함수 추가
                                .build())
                        .build()).build()
        val kaptKotlinGeneratedDir = processingEnv.options["kapt.kotlin.generated"]!!
        testFileBuilder.writeTo(File(kaptKotlinGeneratedDir))                       //파일 생성
    }


}