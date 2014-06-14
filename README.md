ViewPagerIndicator
==================

A lightweight paging indicator view for the `ViewPager` from the
[Android Support Library][1].

This is an extremely lightweight implementation that displays the
pages as horizontally aligned dots. If you need more control over
the paging indicator's display checkout [Jake Wharton][4]'s
[Android ViewPagerIndicator][5] on which this implementation is based.

![ViewPagerIndicator Screenshot][2]




Usage
=====

There is a [sample][3] app provided which demonstrates how to use the library.

Here are the steps required to add a `ViewPagerIndicator` to a `ViewPager`:

  1. Add `ViewPagerIndicator` to your view right below the respective `ViewPager`:

``` xml
<com.github.segoh.viewpagerindicator.ViewPagerIndicator
    android:id="@+id/pager_indicator"
    android:layout_height="wrap_content"
    android:layout_width="match_parent" />
```

2. Bind the `ViewPagerIndicator` to your `ViewPager`:

``` java
@Override
public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

    // Add an adapter to your pager
    ViewPager pager = (ViewPager) findViewById(R.id.pager);
    pager.setAdapter(new PageAdapter(getSupportFragmentManager()));

    // Bind the pager indicator to your pager
    ViewPagerIndicator pagerIndicator = (ViewPagerIndicator) findViewById(R.id.pager_indicator);
    pagerIndicator.setViewPager(pager);
}
```

Please note: If you require an `OnPageChangeListener` on your `ViewPager` set it
on the `ViewPagerIndicator` rather than on the `ViewPager`.




Credits
-------

 * [Jake Wharton][4] - Author of [Android ViewPagerIndicator][5] on which this
   lightweight ViewPagerIndicator implementation is based.
 * [Patrik Åkerfeldt][6] - Author of [ViewFlow][7], a precursor to the ViewPager,
   which supports paged views and is the original source of both the title
   and circle indicators.
 * [Francisco Figueiredo Jr.][8] - Idea and [first implementation][9] for
   fragment support via ViewPager.




License
=======

    Copyright 2014 Sebastian Gutsfeld
    Copyright 2012 Jake Wharton
    Copyright 2011 Patrik Åkerfeldt
    Copyright 2011 Francisco Figueiredo Jr.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.




 [1]: http://developer.android.com/sdk/compatibility-library.html
 [2]: https://raw.github.com/segoh/ViewPagerIndicator/master/sample/screenshot.png
 [3]: https://github.com/segoh/ViewPagerIndicator/tree/master/sample
 [4]: https://github.com/JakeWharton
 [5]: https://github.com/JakeWharton/Android-ViewPagerIndicator
 [6]: https://github.com/pakerfeldt
 [7]: https://github.com/pakerfeldt/android-viewflow
 [8]: https://github.com/franciscojunior
 [9]: https://gist.github.com/1122947
