package com.example.give

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
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
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_product_screen.*
import com.smarteist.autoimageslider.SliderView
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.IndicatorAnimations
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class Home : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mToggle: ActionBarDrawerToggle
    private var doubleBackToExitPressedOnce: Boolean = false
    private val mHandler = Handler()
    private val mRunnable = Runnable { doubleBackToExitPressedOnce = false }
    lateinit var adapter: ProductAdapter
    private lateinit var sharedPreferences: SharedPreferences
    val productList: MutableList<Product> = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        mDrawerLayout = findViewById(R.id.drawLayout)
        mToggle = ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close)
        mDrawerLayout.addDrawerListener(mToggle)
        mToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navigationView.setNavigationItemSelectedListener (this)

        val queue = Volley.newRequestQueue(this)
        val url = "https://mfnodemcu.000webhostapp.com/android/home.php"
        val hplist:MutableList<Hypermarket> = mutableListOf<Hypermarket>()

// Request a string response from the provided URL.
        val stringRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                // Display the first 500 characters of the response string.
                for (i in 0..response.length() - 1) {
                    var jsonObject =  response.getJSONObject(i)
                    hplist.add(
                        Hypermarket(
                            jsonObject.getInt("id"),
                            jsonObject.getString("name"),
                            jsonObject.getString("imageUrl").replace("\\/", "/")
                        )
                    )


                }
                val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
                val adapter = HypermarketAdapter(this)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false) as RecyclerView.LayoutManager?
                adapter.setHypermarketList(hplist)


            },
            Response.ErrorListener { error ->  })

// Add the request to the RequestQueue.
        queue.add(stringRequest)




        val url2 =
            "https://mfnodemcu.000webhostapp.com/android/top20product.php"


// Request a string response from the provided URL.
        val stringRequest2 = JsonArrayRequest(
            Request.Method.GET, url2, null,
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


                val recyclerView = findViewById<RecyclerView>(R.id.homeRVscreen)
                this.adapter = ProductAdapter(this)
                recyclerView.adapter = this.adapter
                recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false) as RecyclerView.LayoutManager?


                adapter.setProductList(productList)



            },
            Response.ErrorListener { error -> productlist_errorMessage.text="No item found"
                Toast.makeText(this,"No result found",Toast.LENGTH_SHORT).show()})

// Add the request to the RequestQueue.
       queue.add(stringRequest2)




    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (mToggle.onOptionsItemSelected(item)) {
            true
        }

        return super.onOptionsItemSelected(item)
    }


    protected override fun onDestroy() {
        super.onDestroy()

        if (mHandler != null) {
            mHandler.removeCallbacks(mRunnable)
        }
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            setResult(Activity.RESULT_CANCELED)
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        mHandler.postDelayed(mRunnable, 2000)
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {

            R.id.nav_logout -> {
                //Toast.makeText(this,"halow",Toast.LENGTH_SHORT).show()
                sharedPreferences = getSharedPreferences("LoginSuccess", Context.MODE_PRIVATE)
                val editor:SharedPreferences.Editor = sharedPreferences.edit()
                editor.remove("user_id")
                editor.apply()
                val intent = getIntent()
                intent.putExtra("logout", true)
                setResult(Activity.RESULT_OK,intent)
                finish()
            }
            R.id.nav_myCart ->{
                val intent = Intent(this,MyCart::class.java)
                startActivity(intent)

            }
            R.id.nav_myOrder ->{
                val intent = Intent(this,MyOrder::class.java)
                startActivity(intent)

            }
            R.id.nav_account ->{
                val intent = Intent(this,MyAccount::class.java)
                startActivity(intent)

            }

        }
        return true
    }


    override fun onPointerCaptureChanged(hasCapture: Boolean) {

    }



}
