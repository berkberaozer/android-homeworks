package com.berkberaozer.hw1.classes

class School {
    private var gradingSystem:String? = null
    private var imgId = 0

    constructor(gradingSystem:String?, imgId: Int) {
        this.gradingSystem = gradingSystem
        this.imgId = imgId
    }

    fun getGradingSystem(): String? {
        return gradingSystem
    }

    fun setGradingSystem(gradingSystem: String?) {
        this.gradingSystem = gradingSystem
    }

    fun getImgId(): Int {
        return imgId
    }

    fun setImgId(imgId: Int) {
        this.imgId = imgId
    }
}