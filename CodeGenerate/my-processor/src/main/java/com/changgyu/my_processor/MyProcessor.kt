package com.changgyu.my_processor

import com.changgyu.my_annotation.MyAnnotation
import com.google.auto.common.MoreElements.getPackage
import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.metadata.KotlinPoetMetadataPreview
import com.squareup.kotlinpoet.metadata.specs.internal.ClassInspectorUtil
import com.squareup.kotlinpoet.metadata.toImmutableKmClass
import sun.rmi.runtime.Log
import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedAnnotationTypes
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement

@KotlinPoetMetadataPreview
@AutoService(Processor::class)
@SupportedAnnotationTypes("com.changgyu.my_annotation.MyAnnotation")
class MyProcessor: AbstractProcessor() {

    lateinit var testFileBuilder: FileSpec

    override fun process(
        annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment
    ): Boolean {
        val annotatedClasses = roundEnv.getElementsAnnotatedWith(MyAnnotation::class.java)
        if(annotatedClasses.isEmpty()) return false

        annotatedClasses.forEach {
            if(it.kind != ElementKind.CLASS) return false
            makeMyInfoPrintClass(it)
        }

        return true
    }

    private fun makeMyInfoPrintClass(element: Element){
        val typeMetadata = element.getAnnotation(Metadata::class.java)
        val kmClass = typeMetadata.toImmutableKmClass()
        val className = ClassInspectorUtil.createClassName(kmClass.name)
        val printInfoFunSpec = FunSpec.builder("printInfo").receiver(className)

        val myAnnotation = element.getAnnotation(MyAnnotation::class.java)
        myAnnotation.run {
            printInfoFunSpec.addStatement("println(%P)", "name : ${this.name}, age : ${this.age}, job : ${this.job}")
        }
        testFileBuilder = FileSpec.builder("","MyInfoPrint").addFunction(printInfoFunSpec.build()).build()
        val kaptKotlinGeneratedDir = processingEnv.options["kapt.kotlin.generated"]!!
        testFileBuilder.writeTo(File(kaptKotlinGeneratedDir))

    }

    private fun makeMyInformationClass(){
        testFileBuilder = FileSpec.builder("","MyInformation") // 파일이름 설정
            .addType(TypeSpec.classBuilder("MyInformation") //클래스 이름 설정
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