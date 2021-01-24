package com.example.give

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.GridLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.give.adapter.HypermarketAdapter
import com.example.give.adapter.ProductAdapter
import com.example.give.classes.Hypermarket
import com.example.give.classes.Product
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_product_screen.*
import java.util.*
import java.util.jar.Attributes

class ProductScreen : AppCompatActivity(), SearchView.OnQueryTextListener {
    val productList: MutableList<Product> = mutableListOf<Product>()
    lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_screen)

        val intent = getIntent()
        setTitle(intent.getStringExtra("HypermarketName"))

        val queue = Volley.newRequestQueue(this)
        val url =
            "https://mfnodemcu.000webhostapp.com/android/product.php?supermarketid=" + intent.getStringExtra(
                "HypermarketID")+"&category="+intent.getStringExtra("category")


// Request a string response from the provided URL.
        val stringRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                // Display the first 500 characters of the response string.
                for (i in 0..response.length() - 1) {
                    var jsonObject = response.getJSONObject(i)
                    productList.add(
                        Product(
                            jsonObject.getInt("id"),
                            jsonObject.getString("name"),
                            jsonObject.getString("desc"),
                            jsonObject.getString("imageUrl").replace("\\/", "/"),
                            jsonObject.getString("expDate"),
                            jsonObject.getString("oriPrice").toDouble(),
                            jsonObject.getString("discPrice").toDouble(),
                            jsonObject.getString("percentage").toInt(),
                            jsonObject.getString("hypermarketId").toInt(),
                            jsonObject.getString("category"),
                            jsonObject.getString("stock").toInt(),
                            jsonObject.getString("qtysold").toInt()

                        )
                    )


                }


                val recyclerView = findViewById<RecyclerView>(R.id.productRVscreen)
                this.adapter = ProductAdapter(this)
                recyclerView.adapter = this.adapter
                recyclerView.layoutManager = GridLayoutManager(this, 3)


                adapter.setProductList(productList)



            },
            Response.ErrorListener { error -> productlist_errorMessage.text="No item found"
                Toast.makeText(this,"No result found",Toast.LENGTH_SHORT).show()})

// Add the request to the RequestQueue.
        queue.add(stringRequest)


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        val menuitem: MenuItem = menu?.findItem(R.id.action_search)!!
        val searchView: SearchView = menuitem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

        R.id.cart -> {
            val intent = Intent(this, MyCart::class.java)
            startActivity(intent)


            true
        }
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }

    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        Toast.makeText(this, query!!, Toast.LENGTH_SHORT).show()
        val newproductList: MutableList<Product> = mutableListOf<Product>()

        for (i in productList) {
            if (i.name.toLowerCase().contains(query))
                newproductList.add(i)

        }

            adapter.setProductList(newproductList)


        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText == "")
            adapter.setProductList(productList)
        return false

    }
}
