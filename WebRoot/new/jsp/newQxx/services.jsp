<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="navbar">
    <div class="navbar-inner">
        <div class="center sliding">工具</div>
    </div>
</div>
<div class="pages">
    <div data-page="services" class="page">
        <form data-search-list=".search-here" data-search-in=".item-title" class="searchbar">
            <div class="searchbar-input">
                <input type="search" placeholder="Search"/><a href="#" class="searchbar-clear"></a>
            </div>
            <a href="#" class="searchbar-cancel">Cancel</a>
        </form>
        <div class="searchbar-overlay"></div>
        <div class="page-content">
            <div class="list-block searchbar-not-found">
                <div style="text-align: center;" class="content-block centered">
                    <img src="new/jsp/newQxx/ico/no-cart.ico"/><br/>
                    没有内容...
                </div>
            </div>
            <div class="list-block search-here searchbar-found">
                <script type="text/template7" id="searchbarData">
                    {{#each this}}
                        <li class="item-content">
                            <div class="item-inner">
                                <div class="item-title">{{this}}</div>
                            </div>
                        </li>
                    {{/each}}
                </script>
                <ul id="searchbarUl">
                    <li class="item-content">
                        <div class="item-inner">
                            <div class="item-title">1231</div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>