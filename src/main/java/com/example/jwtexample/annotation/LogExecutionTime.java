package com.example.jwtexample.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)  // annotation runtimeda ham boladi (Agar SOURCE bolsa faqat compile vaqtida Class bolsa bytecode da lekn runtime yoq)
@Target(ElementType.METHOD) // bu annotation faqat method larga qollaniladi
public @interface LogExecutionTime {
}
