package acr.browser.lightning.browser.view.delegates

import acr.browser.lightning.R
import acr.browser.lightning.browser.view.ViewDelegate
import acr.browser.lightning.databinding.BrowserActivityBottomBinding
import acr.browser.lightning.icon.TabCountView
import acr.browser.lightning.search.SearchView
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView

class BottomTabViewDelegate(binding: BrowserActivityBottomBinding) : ViewDelegate {
    override val root: CoordinatorLayout = binding.root
    override val toolbar: Toolbar = binding.toolbar
    override val contentFrame: FrameLayout = binding.contentFrame
    override val uiLayout: LinearLayout = binding.uiLayout
    override val browserLayoutContainer: FrameLayout = binding.browserLayoutContainer
    override val toolbarLayout: ConstraintLayout = binding.toolbarLayout
    override val drawerLayout: DrawerLayout = binding.drawerLayout
    override val tabDrawer: LinearLayout = binding.tabDrawer
    override val bookmarkDrawer: LinearLayout = binding.bookmarkDrawer
    override val homeImageView: ImageView = binding.homeImageView
    override val tabCountView: TabCountView = binding.tabCountView
    override val drawerTabsList: RecyclerView = binding.drawerTabsList
    override val desktopTabsList: RecyclerView = binding.desktopTabsList
    override val bookmarkListView: RecyclerView = binding.bookmarkListView
    override val searchContainer: ConstraintLayout = binding.searchContainer
    override val search: SearchView = binding.search
    override val findBar: LinearLayout = binding.findBar
    override val findQuery: TextView = binding.findQuery
    override val findPrevious: ImageButton = binding.findPrevious
    override val findNext: ImageButton = binding.findNext
    override val findQuit: ImageButton = binding.findQuit
    override val homeButton: FrameLayout = binding.homeButton
    override val actionBack: ImageView = binding.actionBack
    override val actionForward: ImageView = binding.actionForward
    override val actionHome: ImageView = binding.actionHome
    override val newTabButton: ImageView = binding.newTabButton
    override val searchRefresh: ImageView = binding.searchRefresh
    override val actionAddBookmark: ImageView = binding.actionAddBookmark
    override val actionPageTools: ImageView = binding.actionPageTools
    override val tabHeaderButton: ImageView = binding.tabHeaderButton
    override val bookmarkBackButton: ImageView = binding.bookmarkBackButton
    override val searchSslStatus: ImageView = binding.searchSslStatus
    override val progressView: ProgressBar = binding.progressView
    override val adsBanner: FrameLayout = binding.adsBanner
    override val adsBanner2: FrameLayout = binding.adsBanner2

    // Traffic stats views
    override val trafficStatsContainer: FrameLayout? = binding.root.findViewById(R.id.traffic_stats_container)
    override val trafficFab: CardView? = binding.root.findViewById(R.id.traffic_fab)
    override val trafficStatsCard: CardView? = binding.root.findViewById(R.id.traffic_stats_card)
    override val trafficSessionValue: TextView? = binding.root.findViewById(R.id.traffic_session_value)
    override val trafficSessionWifiValue: TextView? = binding.root.findViewById(R.id.traffic_session_wifi_value)
    override val trafficSessionMobileValue: TextView? = binding.root.findViewById(R.id.traffic_session_mobile_value)
    override val trafficDailyValue: TextView? = binding.root.findViewById(R.id.traffic_daily_value)
    override val trafficDailyWifiValue: TextView? = binding.root.findViewById(R.id.traffic_daily_wifi_value)
    override val trafficDailyMobileValue: TextView? = binding.root.findViewById(R.id.traffic_daily_mobile_value)
    override val trafficMonthlyValue: TextView? = binding.root.findViewById(R.id.traffic_monthly_value)
    override val trafficMonthlyWifiValue: TextView? = binding.root.findViewById(R.id.traffic_monthly_wifi_value)
    override val trafficMonthlyMobileValue: TextView? = binding.root.findViewById(R.id.traffic_monthly_mobile_value)
    override val trafficNetworkType: TextView? = binding.root.findViewById(R.id.traffic_network_type)
    override val trafficNetworkIcon: ImageView? = binding.root.findViewById(R.id.traffic_network_icon)
    override val trafficNetworkInfo: View? = binding.root.findViewById(R.id.traffic_network_info)
}
