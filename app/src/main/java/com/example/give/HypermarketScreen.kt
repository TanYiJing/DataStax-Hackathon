package com.example.give

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_hypermarket_screen.*
import kotlinx.android.synthetic.main.categories.*

class HypermarketScreen : AppCompatActivity() {

    lateinit var  hypermarketname:String
     var hypermarketId:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hypermarket_screen)
        val intent = getIntent()
        hypermarketname=intent.getStringExtra("HypermarketName")
        hypermarketId=intent.getStringExtra("HypermarketID").toInt()

        setTitle(hypermarketname)

        promotion.setOnClickListener{
            viewProduct("pm")
        }

        frozen.setOnClickListener{
            viewProduct("z")
        }

        household.setOnClickListener {
            viewProduct("hh")
        }

        dairy_eggs.setOnClickListener{
            viewProduct("de")
        }
        pantry.setOnClickListener{
            viewProduct("pt")
        }
        beverages.setOnClickListener{
            viewProduct("bv")
        }
        dry_canned.setOnClickListener{
            viewProduct("dc")
        }
        snack.setOnClickListener{

            viewProduct("sn")
        }
        hyperscreenbanner.setOnClickListener{

            viewProduct("pm")

        }

    }



    fun viewProduct(name:String){
        val intent = Intent(this , ProductScreen::class.java)
        intent.putExtra("HypermarketName",hypermarketname)
        intent.putExtra("HypermarketID",hypermarketId.toString())
        intent.putExtra("category",name)
        startActivity(intent)

    }




}
