package com.example.dashscrumboard.ui.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.viewpager2.widget.ViewPager2
import com.example.dashscrumboard.R
import com.example.dashscrumboard.data.model.Status
import com.example.dashscrumboard.databinding.ActivityTaskBoardBinding
import com.example.dashscrumboard.ui.create.CreateTaskActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class TaskBoardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTaskBoardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setupViewPager()
    }

    private fun setupViewPager() {
        val viewPager: ViewPager2 = binding.viewPager
        val tabLayout: TabLayout = binding.tabLayout
        val tabTitles = arrayOf("To Do", "In Progress", "Done")
        val adapter = ViewPagerAdapter(this)
        adapter.addFragment(TaskListFragment.newInstance(Status.TODO), tabTitles[0])
        adapter.addFragment(TaskListFragment.newInstance(Status.IN_PROGRESS), tabTitles[1])
        adapter.addFragment(TaskListFragment.newInstance(Status.DONE), tabTitles[2])
        viewPager.adapter = adapter
        viewPager.currentItem = 0
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = adapter.getTabTitle(position)
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.add_task_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.addTask -> {
                // go to CreateTaskActivity
                val intent = Intent(this, CreateTaskActivity::class.java).apply {
                    putExtra(CreateTaskActivity.TYPE, "CREATE")
                }
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}