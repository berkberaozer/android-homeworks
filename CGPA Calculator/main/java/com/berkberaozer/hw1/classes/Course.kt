package com.berkberaozer.hw1.classes

import android.os.Parcel
import android.os.Parcelable

class Course : Parcelable {
    companion object CREATOR : Parcelable.Creator<Course> {
        override fun createFromParcel(parcel: Parcel): Course {
            return Course(parcel)
        }

        override fun newArray(size: Int): Array<Course?> {
            return arrayOfNulls(size)
        }
        public var courseCount: Int = 0
        public fun getCourseCounts(): Int { return courseCount }
    }
    private var name: String? = null
    private var credit: Int
    private var gradeId: Int

    constructor(name: String?, credit: Int, gradeId: Int) {
        this.name = name
        this.credit = credit
        this.gradeId = gradeId
    }

    constructor(credit: Int, gradeId: Int) {
        this.name = "No Name"
        this.credit = credit
        this.gradeId = gradeId
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getCredit(): Int {
        return credit
    }

    fun setCredit(credit: Int) {
        this.credit = credit
    }

    fun getGradeId(): Int {
        return gradeId
    }

    fun setGradeId(gradeId: Int) {
        this.gradeId = gradeId
    }

    override fun toString(): String {
        return "Course Name: $name\nCourse Credit: $credit\nGrade ID: $gradeId\n\n"
    }

    protected constructor(`in`: Parcel) {
        name = `in`.readString()
        credit = `in`.readInt()
        gradeId = `in`.readInt()
    }
    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeString(name)
        credit?.let { parcel.writeInt(it) }
        gradeId?.let { parcel.writeInt(it) }
    }
    override fun describeContents(): Int {
        return 0
    }
}