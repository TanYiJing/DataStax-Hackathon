package com.example.give.classes

import java.util.*

class Product(val id :Int ,
              val name :String ,
              val desc:String ,
              val imageUrl:String ,
              val expdate: String,
              val oriPrice : Double,
              val discPrice: Double,
              val percentage: Int,
              val hypermarketID: Int,
              val category: String,
              val stock : Int,
              val qtySold:Int
) {
}