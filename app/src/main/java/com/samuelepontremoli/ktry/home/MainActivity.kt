package com.samuelepontremoli.ktry.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.samuelepontremoli.ktry.R

/**
 * Created by samuele on 01/07/17.
 * Main class
 */
class MainActivity : AppCompatActivity() {

    val drawerLayout: DrawerLayout by lazy {
        findViewById(R.id.drawer_layout) as DrawerLayout
    }
    private val toolbar: Toolbar by lazy {
        findViewById(R.id.main_toolbar) as Toolbar
    }
    private val toggle: ActionBarDrawerToggle by lazy {
        ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initDrawer()

        changeFragment(HomeFragment.newInstance(), true)
    }

    private fun initDrawer() {
        setSupportActionBar(toolbar)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    fun changeFragment(f: Fragment, cleanStack: Boolean = false) {
        val ft = supportFragmentManager.beginTransaction()
        if (cleanStack) {
            clearBackStack()
        }
        ft.setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out, R.anim.abc_popup_enter, R.anim.abc_popup_exit)
        ft.replace(R.id.fragment_container, f)
        ft.addToBackStack(null)
        ft.commit()
    }

    fun clearBackStack() {
        val manager = supportFragmentManager
        if (manager.backStackEntryCount > 0) {
            val first = manager.getBackStackEntryAt(0)
            manager.popBackStack(first.id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

}