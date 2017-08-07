/**
 *                                                        ____   _____
 *  Dynarch Calendar -- JSCal2, version 1.9               \  /_  /   /
 *  Built at 2011/08/16 16:55 GMT                          \  / /   /
 *                                                          \/ /_  /
 *  (c) Dynarch.com 2009                                     \  / /
 *  All rights reserved.                                       / /
 *  Visit www.dynarch.com/projects/calendar for details        \/
 *
 */
Calendar = (function(){

        // Constructor.  Ignore the "ugly" name, it's a convention that I
        // developed while working on DynarchLIB (www.dynarchlib.com).  This
        // variable is returned from the function, so it becomes assigned to
        // window.Calendar
        function D(q) {
                q = q || {};
                this.args = q = DEF(q, {
                        animation     : !is_ie6,
                        cont          : null,
                        bottomBar     : true,
                        date          : true,
                        fdow          : _("fdow"),
                        min           : null, // minimum date
                        max           : null, // maximum date
                        reverseWheel  : false,
                        selection     : [],
                        selectionType : D.SEL_SINGLE,
                        weekNumbers   : false,
                        align         : "Bl/ / /T/r",
                        inputField    : null,
                        trigger       : null,
                        dateFormat    : "%Y-%m-%d",
                        fixed         : false,
                        opacity       : is_ie ? 1 : 3,
                        titleFormat   : "%b %Y",
                        showTime      : false,
                        timePos       : "right",
                        time          : true,
                        minuteStep    : 5,
                        noScroll      : false,

                        disabled      : Noop,
                        checkRange    : false,

                        dateInfo      : Noop,
                        onChange      : Noop,
                        onSelect      : Noop,
                        onTimeChange  : Noop,
                        onFocus       : Noop,
                        onBlur        : Noop
                });

                this.handlers = {};
                var self = this, today = new Date();

                q.min = readDate(q.min);
                q.max = readDate(q.max);
                if (q.date === true)
                        q.date = today;
                if (q.time === true)
                        q.time = today.getHours() * 100 + Math.floor(today.getMinutes() / q.minuteStep) * q.minuteStep;
                this.date = readDate(q.date);
                this.time = q.time;
                this.fdow = q.fdow;
                foreach("onChange onSelect onTimeChange onFocus onBlur".split(/\s+/), function(ev){
                        var handler = q[ev];
                        if (!(handler instanceof Array))
                                handler = [ handler ];
                        self.handlers[ev] = handler;
                });
                this.selection = new D.Selection(q.selection, q.selectionType, _onSelChange, this);

                var el = _create(this);
                if (q.cont)
                        $(q.cont).appendChild(el);

                if (q.trigger)
                        this.manageFields(q.trigger, q.inputField, q.dateFormat);
        };

        var     UA       = navigator.userAgent,
                is_opera = /opera/i.test(UA),
                is_khtml = /Konqueror|Safari|KHTML/i.test(UA),
                is_ie    = /msie/i.test(UA) && !is_opera && !(/mac_powerpc/i.test(UA)),
                is_ie6   = is_ie && /msie 6/i.test(UA),
                is_gecko = /gecko/i.test(UA) && !is_khtml && !is_opera && !is_ie,
                P        = D.prototype,
                I18N     = D.I18N = {};

        D.SEL_NONE = 0;
        D.SEL_SINGLE = 1;
        D.SEL_MULTIPLE = 2;
        D.SEL_WEEK = 3;

        D.dateToInt = dateToInt;
        D.intToDate = intToDate;
        D.printDate = printDate;
        D.formatString = formatString;
        D.i18n = _;

        D.LANG = function(code, name, data) {
                I18N.__ = I18N[code] = {
                        name: name,
                        data: data
                };
        };

        D.setup = function(args) { return new D(args) };

        P.moveTo = function(date, anim) {
                var self = this;
                date = readDate(date);
                var back = compareDates(date, self.date, true),
                	brake,
                	q = self.args, isMin = q.min && compareDates(date, q.min), isMax = q.max && compareDates(date, q.max);
                // if (q.showTime) {
                //         self.setHours(date.getHours());
                //         self.setMinutes(date.getMinutes());
                // }
                if (!q.animation)
                        anim = false;
                condClass(isMin != null && isMin <= 1, [ self.els.navPrevMonth, self.els.navPrevYear ], "DynarchCalendar-navDisabled");
                condClass(isMax != null && isMax >= -1, [ self.els.navNextMonth, self.els.navNextYear ], "DynarchCalendar-navDisabled");
                if (isMin < -1) {
                        date = q.min;
                        brake = 1;
                        back = 0;
                }
                if (isMax > 1) {
                        date = q.max;
                        brake = 2;
                        back = 0;
                }
                self.date = date;
                self.refresh(!!anim);
                self.callHooks("onChange", self, date, anim);
                if (anim && !(back == 0 && anim == 2)) {
                        if (self._bodyAnim)
                                self._bodyAnim.stop();

                        var     body   = self.els.body,
                                div    = CE("div", "DynarchCalendar-animBody-" + ANIMBODY_CLASSES[back], body),
                                table  = body.firstChild,
                                opac   = opacity(table) || 0.7,
                                fe     = brake ? easing.brakes : back == 0 ? easing.shake : easing.accel_ab2,
                                vert   = back * back > 4,
                                padd   = vert ? table.offsetTop : table.offsetLeft,
                                st     = div.style,
                                dist   = vert ? body.offsetHeight : body.offsetWidth;

                        if (back < 0)
                                dist += padd;
                        else if (back > 0)
                                dist = padd - dist;
                        else {
                                dist = Math.round(dist / 7);
                                if (brake == 2)
                                        dist = -dist;
                        }

                        if (!brake && back != 0) {
                                var div2 = div.cloneNode(true), st2 = div2.style, dist2 = 2 * dist;
                                div2.appendChild(table.cloneNode(true));
                                st2[vert ? "marginTop" : "marginLeft"] = dist + "px";
                                body.appendChild(div2);
                        }

                        table.style.visibility = "hidden";

                        div.innerHTML = _buildBodyHTML(self);
                        self._bodyAnim = animation({
                                onUpdate: function(t, map){
                                        var fet = fe(t);
                                        if (div2)
                                                var m2 = map(fet, dist, dist2) + "px";
                                        if (brake) {
                                                st[vert ? "marginTop" : "marginLeft"] = map(fet, dist, 0) + "px";
                                        } else {
                                                if (vert || back == 0) {
                                                        st.marginTop = map(back == 0 ? fe(t * t) : fet, 0, dist) + "px";
                                                        if (back != 0)
                                                                st2.marginTop = m2;
                                                }
                                                if (!vert || back == 0) {
                                                        st.marginLeft = map(fet, 0, dist) + "px";
                                                        if (back != 0)
                                                                st2.marginLeft = m2;
                                                }
                                        }
                                        if (self.args.opacity > 2 && div2) {
                                                opacity(div2, 1 - fet);
                                                opacity(div, fet);
                                        }
                                },
                                onStop: function(t){
                                        body.innerHTML = _buildBodyHTML(self, date);
                                        self._bodyAnim = null;
                                }
                        });
                }
                self._lastHoverDate = null;
                return isMin >= -1 && isMax <= 1;
        };

        P.isDisabled = function(date) {
                var q = this.args;
                return (q.min && compareDates(date, q.min) < 0) ||
                        (q.max && compareDates(date, q.max) > 0) ||
                        q.disabled(date);
        };

        P.toggleMenu = function() { _doMenu(this, !this._menuVisible) };

        P.refresh = function(noBody) {
                var e = this.els;
                if (!noBody)
                        e.body.innerHTML = _buildBodyHTML(this);
                e.title.innerHTML = _buildTitleHTML(this);
                e.yearInput.value = this.date.getFullYear();
        };

        P.redraw = function() {
                var self = this, e = self.els;
                self.refresh();
                e.dayNames.innerHTML = _buildDayNamesHTML(self);
                e.menu.innerHTML = _buildMenuHTML(self);
                if (e.bottomBar)
                        e.bottomBar.innerHTML = _buildBottomBarHTML(self);
                walk(e.topCont, function(el){
                        var name = INTERESTING_ELEMENTS[el.className];
                        if (name)
                                e[name] = el;
                        if (el.className == "DynarchCalendar-menu-year") {
                                addEvent(el, self._focusEvents);
                                e.yearInput = el;
                        } else if (is_ie)
                                el.setAttribute("unselectable", "on");
                });
                self.setTime(null, true);
        };

        P.setLanguage = function(code) {
                var lang = D.setLanguage(code);
                if (lang) {
                        this.fdow = lang.data.fdow;
                        this.redraw();
                }
        };

        D.setLanguage = function(code) {
                var lang = I18N[code];
                if (lang)
                        I18N.__ = lang;
                return lang;
        };

        P.focus = function() {
                try { this.els[this._menuVisible ? "yearInput" : "focusLink"].focus(); } catch(ex) {};
                _setFocus.call(this);
        };

        P.blur = function() {
                this.els.focusLink.blur();
                this.els.yearInput.blur();
                _setBlur.call(this);
        };

        P.showAt = function(x, y, anim) {
                if (this._showAnim)
                        this._showAnim.stop();
                anim = anim && this.args.animation;
                var top = this.els.topCont, self = this, body = this.els.body.firstChild, h = body.offsetHeight, s = top.style;
                s.position = "absolute";
                s.left = x + "px";
                s.top = y + "px";
                s.zIndex = 10000;
                s.display = "";
                if (anim) {
                        body.style.marginTop = -h + "px";
                        this.args.opacity > 1 && opacity(top, 0);
                        this._showAnim = animation({
                                onUpdate: function(t, map) {
                                        body.style.marginTop = -map(easing.accel_b(t), h, 0) + "px";
                                        self.args.opacity > 1 && opacity(top, t);
                                },
                                onStop: function() {
                                        self.args.opacity > 1 && opacity(top, "");
                                        self._showAnim = null;
                                }
                        });
                }
        };

        P.hide = function() {
                var top = this.els.topCont, self = this, body = this.els.body.firstChild, h = body.offsetHeight, pos = getPos(top).y;
                if (this.args.animation) {
                        if (this._showAnim)
                                this._showAnim.stop();
                        this._showAnim = animation({
                                onUpdate: function(t, map){
                                        self.args.opacity > 1 && opacity(top, 1 - t);
                                        body.style.marginTop = -map(easing.accel_b(t), 0, h) + "px";
                                        top.style.top = map(easing.accel_ab(t), pos, pos - 10) + "px";
                                }, onStop: function(){
                                        top.style.display = "none";
                                        body.style.marginTop = "";
                                        self.args.opacity > 1 && opacity(top, "");
                                        self._showAnim = null;
                                }
                        });
                } else
                        top.style.display = "none";
                this.inputField = null;
        };

        P.popup = function(el, align) {
                el = $(el);
                if (!align)
                        align = this.args.align;
                align = align.split(/\x2f/);
                var anchor = getPos(el), top = this.els.topCont, s = top.style, sz, wp = getViewport();
                s.visibility = "hidden";
                s.display = "";
                this.showAt(0, 0);
                document.body.appendChild(top);
                sz = { x: top.offsetWidth, y: top.offsetHeight };
                function go(how) {
                        var p = { x: pos.x, y: pos.y };
                        if (!how)
                                return p;
                        // "tr" is implied since getPos returns the upper-left corner
                        if (/B/.test(how))
                                p.y += el.offsetHeight;
                        if (/b/.test(how))
                                p.y += el.offsetHeight - sz.y;
                        if (/T/.test(how))
                                p.y -= sz.y;
                        if (/l/.test(how))
                                p.x -= sz.x - el.offsetWidth;
                        if (/L/.test(how))
                                p.x -= sz.x;
                        if (/R/.test(how))
                                p.x += el.offsetWidth;
                        if (/c/i.test(how))
                                p.x += (el.offsetWidth - sz.x) / 2;
                        if (/m/i.test(how))
                                p.y += (el.offsetHeight - sz.y) / 2;
                        return p;
                };
                var pos = anchor;
                pos = go(align[0]); // preferred pos
                if (pos.y < wp.y) {
                        pos.y = anchor.y;
                        pos = go(align[1]);
                }
                if (pos.x + sz.x > wp.x + wp.w) {
                        pos.x = anchor.x;
                        pos = go(align[2]);
                }
                if (pos.y + sz.y > wp.y + wp.h) {
                        pos.y = anchor.y;
                        pos = go(align[3]);
                }
                if (pos.x < wp.x) {
                        pos.x = anchor.x;
                        pos = go(align[4]);
                }
                this.showAt(pos.x, pos.y, true);
                s.visibility = "";
                this.focus();
        };

        P.manageFields = function(trigger, input, dateFormat) {
                var self = this;
                input = $(input);
                trigger = $(trigger);
                if (/^button$/i.test(trigger.tagName))
                        trigger.setAttribute("type", "button");
                addEvent(trigger, "click", function(){
                        self.inputField = input;
                        self.dateFormat = dateFormat;
                        if (self.selection.type == D.SEL_SINGLE) {
                                var val, mmon, mday, monthFirst;
                                val = /input|textarea/i.test(input.tagName) ? input.value : (input.innerText || input.textContent);
                                if (val) {
                                        mmon = /(^|[^%])%[bBmo]/.exec(dateFormat);
                                        mday = /(^|[^%])%[de]/.exec(dateFormat);
                                        if (mmon && mday)
                                                monthFirst = mmon.index < mday.index;
                                        val = Calendar.parseDate(val, monthFirst);
                                        if (val) {
                                                self.selection.set(val, false, true);
                                                if (self.args.showTime) {
                                                        self.setHours(val.getHours());
                                                        self.setMinutes(val.getMinutes());
                                                }
                                                self.moveTo(val);
                                        }
                                }
                        }
                        self.popup(trigger);
                });
        };

        P.callHooks = function(ev) {
                var q = Array$(arguments, 1), a = this.handlers[ev], i = 0;
                for (; i < a.length; ++i)
                        a[i].apply(this, q);
        };

        P.addEventListener = function(ev, func) { this.handlers[ev].push(func) };

        P.removeEventListener = function(ev, func) {
                var a = this.handlers[ev], i = a.length;
                while (--i >= 0)
                        if (a[i] === func)
                                a.splice(i, 1);
        };

        P.getTime = function() {
                return this.time;
        };

        P.setTime = function(time, nohooks) {
                if (this.args.showTime) {
                        time = time != null ? time : this.time;
                        this.time = time;
                        var h = this.getHours(), m = this.getMinutes(), am = h < 12;
                        if (this.args.showTime == 12) {
                                if (h == 0) h = 12;
                                if (h > 12) h -= 12;
                                this.els.timeAM.innerHTML = _(am ? "AM" : "PM");
                        }
                        if (h < 10) h = "0" + h;
                        if (m < 10) m = "0" + m;
                        this.els.timeHour.innerHTML = h;
                        this.els.timeMinute.innerHTML = m;
                        if (!nohooks)
                                this.callHooks("onTimeChange", this, time);
                }
        };

        P.getHours = function() {
                return Math.floor(this.time / 100);
        };

        P.getMinutes = function() {
                return this.time % 100;
        };

        P.setHours = function(h) {
                if (h < 0) h += 24;
                this.setTime(100 * (h % 24) + this.time % 100);
        };

        P.setMinutes = function(m) {
                if (m < 0) m += 60;
                m = Math.floor(m / this.args.minuteStep) * this.args.minuteStep;
                this.setTime(100 * this.getHours() + (m % 60));
        };

        P._getInputYear = function() {
                var y = parseInt(this.els.yearInput.value, 10);
                if (isNaN(y))
                        y = this.date.getFullYear();
                return y;
        };

        P._showTooltip = function(date) {
                var html = "", info, tt = this.els.tooltip;
                if (date) {
                        date = intToDate(date);
                        info = this.args.dateInfo(date);
                        if (info && info.tooltip)
                                html = "<div class='DynarchCalendar-tooltipCont'>" + printDate(date, info.tooltip) + "</div>";
                }
                tt.innerHTML = html;
        };

        /* -----[ internals ]----- */

        var TBL_PROPS = " align='center' cellspacing='0' cellpadding='0'";

        function _buildDayNamesHTML(self) {
                var html = [ "<table", TBL_PROPS, "><tr>" ], i = 0;
                if (self.args.weekNumbers)
                        html.push("<td><div class='DynarchCalendar-weekNumber'>", _("wk"), "</div></td>");
                while (i < 7) {
                        var d = (i++ + self.fdow) % 7;
                        html.push("<td><div",
                                  _("weekend").indexOf(d) >= 0 ? " class='DynarchCalendar-weekend'>" : ">",
                                  _("sdn")[d], "</div></td>");
                }
                html.push("</tr></table>");
                return html.join("");
        };

        function _buildBodyHTML(self, date, fdow) {
                date = date || self.date;
                fdow = fdow || self.fdow;
                date = new Date(date.getFullYear(), date.getMonth(), date.getDate(), 12, 0, 0, 0);
                var month = date.getMonth(), html = [], k = 0, wn = self.args.weekNumbers;

                // calendar voodoo for computing the first day that would actually be
                // displayed in the calendar, even if it's from the previous month.
                // WARNING: this is magic. ;-)
                date.setDate(1);
                var day1 = (date.getDay() - fdow) % 7;
                if (day1 < 0)
                        day1 += 7;
                date.setDate(0-day1); // workaround Chrome bug.
                date.setDate(date.getDate() + 1);

                var today = new Date(),
                        today_d = today.getDate(),
                        today_m = today.getMonth(),
                        today_y = today.getFullYear();

                html[k++] = "<table class='DynarchCalendar-bodyTable'" + TBL_PROPS + ">";
                for (var i = 0; i < 6; ++i) {
                        html[k++] = "<tr class='DynarchCalendar-week";
                        if (i == 0)
                                html[k++] = " DynarchCalendar-first-row";
                        if (i == 5)
                                html[k++] = " DynarchCalendar-last-row";
                        html[k++] = "'>";
                        if (wn)
                                html[k++] = "<td class='DynarchCalendar-first-col'><div class='DynarchCalendar-weekNumber'>" +
                                        getWeekNumber(date) + "</div></td>";
                        for (var j = 0; j < 7; ++j) {
                                var d = date.getDate(), m = date.getMonth(), y = date.getFullYear(),
                                        asInt = 10000 * y + 100 * (m + 1) + d,
                                        isSel = self.selection.isSelected(asInt),
                                        dis = self.isDisabled(date);
                                html[k++] = "<td class='";
                                if (j == 0 && !wn)
                                        html[k++] = " DynarchCalendar-first-col";
                                if (j == 0 && i == 0)
                                        self._firstDateVisible = asInt;
                                if (j == 6) {
                                        html[k++] = " DynarchCalendar-last-col";
                                        if (i == 5)
                                                self._lastDateVisible = asInt;
                                }
                                if (isSel)
                                        html[k++] = " DynarchCalendar-td-selected";
                                html[k++] = "'><div dyc-type='date' unselectable='on' dyc-date='" + asInt + "' ";
                                if (dis)
                                        html[k++] = "disabled='1' ";
                                html[k++] = "class='DynarchCalendar-day";
                                if (_("weekend").indexOf(date.getDay()) >= 0)
                                        html[k++] = " DynarchCalendar-weekend";
                                if (m != month)
                                        html[k++] = " DynarchCalendar-day-othermonth";
                                if (d == today_d && m == today_m && y == today_y)
                                        html[k++] = " DynarchCalendar-day-today";
                                if (dis)
                                        html[k++] = " DynarchCalendar-day-disabled";
                                if (isSel)
                                        html[k++] = " DynarchCalendar-day-selected";
                                dis = self.args.dateInfo(date);
                                if (dis && dis.klass)
                                        html[k++] = " " + dis.klass;
                                html[k++] = "'>" + d + "</div></td>";
                                date = new Date(y, m, d + 1, 12, 0, 0, 0);
                        }
                        html[k++] = "</tr>";
                }
                html[k++] = "</table>";

                return html.join("");
        };

        function _buildHTML(self) {
                var html = [
                        "<table class='DynarchCalendar-topCont'", TBL_PROPS, "><tr><td>",//@JOIN
                        "<div class='DynarchCalendar'>",

                        !is_ie
                                ? "<button class='DynarchCalendar-focusLink'></button>"
                                : "<a class='DynarchCalendar-focusLink' href='#'></a>",

                        "<div class='DynarchCalendar-topBar'>",//@JOIN

                        "<div dyc-type='nav' dyc-btn='-Y' dyc-cls='hover-navBtn,pressed-navBtn' ",//@JOIN
                        "class='DynarchCalendar-navBtn DynarchCalendar-prevYear'><div></div></div>",//@JOIN

                        "<div dyc-type='nav' dyc-btn='+Y' dyc-cls='hover-navBtn,pressed-navBtn' ",//@JOIN
                        "class='DynarchCalendar-navBtn DynarchCalendar-nextYear'><div></div></div>",//@JOIN

                        "<div dyc-type='nav' dyc-btn='-M' dyc-cls='hover-navBtn,pressed-navBtn' ",//@JOIN
                        "class='DynarchCalendar-navBtn DynarchCalendar-prevMonth'><div></div></div>",//@JOIN

                        "<div dyc-type='nav' dyc-btn='+M' dyc-cls='hover-navBtn,pressed-navBtn' ",//@JOIN
                        "class='DynarchCalendar-navBtn DynarchCalendar-nextMonth'><div></div></div>",//@JOIN

                        "<table class='DynarchCalendar-titleCont'", TBL_PROPS, "><tr><td>",//@JOIN
                        "<div dyc-type='title' dyc-btn='menu' dyc-cls='hover-title,pressed-title' class='DynarchCalendar-title'>",
                        _buildTitleHTML(self),
                        "</div></td></tr></table>",//@JOIN

                        "<div class='DynarchCalendar-dayNames'>", _buildDayNamesHTML(self), "</div>",//@JOIN
                        "</div>",//@JOIN
                        "<div class='DynarchCalendar-body'></div>"
                ];
                if (self.args.bottomBar || self.args.showTime)
                        html.push("<div class='DynarchCalendar-bottomBar'>", _buildBottomBarHTML(self), "</div>");
                html.push(
                        "<div class='DynarchCalendar-menu' style='display: none'>", _buildMenuHTML(self), "</div>",//@JOIN
                        "<div class='DynarchCalendar-tooltip'></div>",//@JOIN
                        "</div>",//@JOIN
                        "</td></tr></table>"
                );
                return html.join("");
        };

        function _buildTitleHTML(self) { return "<div unselectable='on'>" + printDate(self.date, self.args.titleFormat) + "</div>" };

        function _buildMenuHTML(self) {
                var html = [
                        "<table height='100%'", TBL_PROPS, "><tr><td>",//@JOIN
                        "<table style='margin-top: 1.5em'", TBL_PROPS, ">",//@JOIN

                        "<tr><td colspan='3'><input dyc-btn='year' class='DynarchCalendar-menu-year' size='6' value='",
                        self.date.getFullYear(),
                        "' /></td></tr>",//@JOIN

                        "<tr><td><div dyc-type='menubtn' dyc-cls='hover-navBtn,pressed-navBtn' dyc-btn='today'>", _("goToday"), "</div></td></tr>",//@JOIN
                        "</table>",//@JOIN
                        "<p class='DynarchCalendar-menu-sep'>&nbsp;</p>",//@JOIN
                        "<table class='DynarchCalendar-menu-mtable'", TBL_PROPS, ">"
                ], mn = _("smn"), i = 0, k = html.length, j;
                while (i < 12) {
                        html[k++] = "<tr>";
                        for (j = 4; --j > 0;)
                                html[k++] = "<td><div dyc-type='menubtn' dyc-cls='hover-navBtn,pressed-navBtn' dyc-btn='m" + i
                                        + "' class='DynarchCalendar-menu-month'>"
                                        + mn[i++] + "</div></td>";
                        html[k++] = "</tr>";
                }
                html[k++] = "</table></td></tr></table>";
                return html.join("");
        };

        function _buildTimeHTML(self, html) {
                html.push(
                        "<table class='DynarchCalendar-time'" + TBL_PROPS + "><tr>",//@JOIN
                        "<td rowspan='2'><div dyc-type='time-hour' dyc-cls='hover-time,pressed-time' class='DynarchCalendar-time-hour'></div></td>",//@JOIN
                        "<td dyc-type='time-hour+' dyc-cls='hover-time,pressed-time' class='DynarchCalendar-time-up'></td>",//@JOIN
                        "<td rowspan='2' class='DynarchCalendar-time-sep'></td>",//@JOIN
                        "<td rowspan='2'><div dyc-type='time-min' dyc-cls='hover-time,pressed-time' class='DynarchCalendar-time-minute'></div></td>",//@JOIN
                        "<td dyc-type='time-min+' dyc-cls='hover-time,pressed-time' class='DynarchCalendar-time-up'></td>"
                );
                if (self.args.showTime == 12) html.push(
                        "<td rowspan='2' class='DynarchCalendar-time-sep'></td>",//@JOIN
                        "<td rowspan='2'><div class='DynarchCalendar-time-am' dyc-type='time-am' dyc-cls='hover-time,pressed-time'></div></td>"
                );
                html.push(
                        "</tr><tr>",//@JOIN
                        "<td dyc-type='time-hour-' dyc-cls='hover-time,pressed-time' class='DynarchCalendar-time-down'></td>",//@JOIN
                        "<td dyc-type='time-min-' dyc-cls='hover-time,pressed-time' class='DynarchCalendar-time-down'></td>",//@JOIN
                        "</tr></table>"
                );
        };

        function _buildBottomBarHTML(self) {
                var html = [], args = self.args;
                html.push("<table", TBL_PROPS, " style='width:100%'><tr>");
                function time() {
                        if (args.showTime) {
                                html.push("<td>");
                                _buildTimeHTML(self, html);
                                html.push("</td>");
                        }
                };
                if (args.timePos == "left")
                        time();
                if (args.bottomBar) {
                        html.push("<td>");
                        html.push("<table", TBL_PROPS, "><tr><td>",//@JOIN
                                  "<div dyc-btn='today' dyc-cls='hover-bottomBar-today,pressed-bottomBar-today' dyc-type='bottomBar-today' ",//@JOIN
                                  "class='DynarchCalendar-bottomBar-today'>",//@JOIN
                                  _("today"),
                                  "</div>",//@JOIN
                                  "</td></tr></table>");
                        html.push("</td>");
                }
                if (args.timePos == "right")
                        time();
                html.push("</tr></table>");
                return html.join("");
        };

        var INTERESTING_ELEMENTS = {
                "DynarchCalendar-topCont"     : "topCont",
                "DynarchCalendar-focusLink"   : "focusLink",
                "DynarchCalendar"             : "main",
                "DynarchCalendar-topBar"      : "topBar",
                "DynarchCalendar-title"       : "title",
                "DynarchCalendar-dayNames"    : "dayNames",
                "DynarchCalendar-body"        : "body",
                "DynarchCalendar-menu"        : "menu",
                "DynarchCalendar-menu-year"   : "yearInput",
                // "DynarchCalendar-menu-mtable" : "menuTable",
                "DynarchCalendar-bottomBar"   : "bottomBar",
                "DynarchCalendar-tooltip"     : "tooltip",
                "DynarchCalendar-time-hour"   : "timeHour",
                "DynarchCalendar-time-minute" : "timeMinute",
                "DynarchCalendar-time-am"     : "timeAM",

                "DynarchCalendar-navBtn DynarchCalendar-prevYear"    : "navPrevYear",
                "DynarchCalendar-navBtn DynarchCalendar-nextYear"    : "navNextYear",
                "DynarchCalendar-navBtn DynarchCalendar-prevMonth"   : "navPrevMonth",
                "DynarchCalendar-navBtn DynarchCalendar-nextMonth"   : "navNextMonth"
        };

        function _create(self) {
                var div = CE("div"), els = self.els = {}, evs = {
                        mousedown  : curry(_onMouseBtn, self, true),
                        mouseup    : curry(_onMouseBtn, self, false),
                        mouseover  : curry(_onMouseOver, self, true),
                        mouseout   : curry(_onMouseOver, self, false),
                        keypress   : curry(_onKeyPress, self)
                };
                if (!self.args.noScroll)
                        evs[is_gecko ? "DOMMouseScroll" : "mousewheel"] = curry(_onMouseWheel, self);
                if (is_ie) {
                        evs.dblclick = evs.mousedown;
                        evs.keydown = evs.keypress;
                }
                div.innerHTML = _buildHTML(self);
                walk(div.firstChild, function(el){
                        var name = INTERESTING_ELEMENTS[el.className];
                        if (name)
                                els[name] = el;
                        if (is_ie)
                                el.setAttribute("unselectable", "on");
                });
                addEvent(els.main, evs);
                addEvent([ els.focusLink, els.yearInput ], self._focusEvents = {
                        focus : curry(_setFocus, self),
                        blur  : curry(_setBlurTimeout, self)
                });
                self.moveTo(self.date, false);
                self.setTime(null, true);
                return els.topCont;
        };

        function _setFocus() {
                if (this._bluringTimeout)
                        clearTimeout(this._bluringTimeout);
                this.focused = true;
                addClass(this.els.main, "DynarchCalendar-focused");
                this.callHooks("onFocus", this);
        };

        function _setBlur() {
                this.focused = false;
                delClass(this.els.main, "DynarchCalendar-focused");
                if (this._menuVisible)
                        _doMenu(this, false);
                if (!this.args.cont)
                        this.hide();
                this.callHooks("onBlur", this);
        };

        function _setBlurTimeout() { this._bluringTimeout = setTimeout(curry(_setBlur, this), 50) };

        function _updateTime(type) {
                switch (type) {
                case "time-hour+": this.setHours(this.getHours() + 1); break;
                case "time-hour-": this.setHours(this.getHours() - 1); break;
                case "time-min+": this.setMinutes(this.getMinutes() + this.args.minuteStep); break;
                case "time-min-": this.setMinutes(this.getMinutes() - this.args.minuteStep); break;
                default: return;
                }
        };

        var ANIMBODY_CLASSES = {
                "-3" : "backYear",
                "-2" : "back",
                "0"  : "now",
                "2"  : "fwd",
                "3"  : "fwdYear"
        };

        function _navClick(self, nav, noAnim) {
                if (this._bodyAnim)
                        this._bodyAnim.stop();
                var date;
                if (nav != 0) {
                        date = new Date(self.date);
                        date.setDate(1);
                        switch (nav) {
                            case "-Y":
                            case -2:
                                date.setFullYear(date.getFullYear() - 1);
                                break;

                            case "+Y":
                            case 2:
                                date.setFullYear(date.getFullYear() + 1);
                                break;

                            case "-M":
                            case -1:
                                date.setMonth(date.getMonth() - 1);
                                break;

                            case "+M":
                            case 1:
                                date.setMonth(date.getMonth() + 1);
                                break;
                        }
                } else
                        date = new Date();
                return self.moveTo(date, !noAnim);
        };

        function _doMenu(self, show) {
                self._menuVisible = show;
                condClass(show, self.els.title, "DynarchCalendar-pressed-title");
                var menu = self.els.menu;
                if (is_ie6)
                        menu.style.height = self.els.main.offsetHeight + "px";
                if (!self.args.animation) {
                        display(menu, show);
                        if (self.focused)
                                self.focus();
                } else {
                        if (self._menuAnim)
                                self._menuAnim.stop();
                        var height = self.els.main.offsetHeight;
                        if (is_ie6)
                                // opacity requires explicit width != 100%.  WTF.
                                menu.style.width = self.els.topBar.offsetWidth + "px";
                        if (show) {
                                menu.firstChild.style.marginTop = -height + "px";
                                self.args.opacity > 0 && opacity(menu, 0);
                                display(menu, true);
                        }
                        self._menuAnim = animation({
                                onUpdate: function(t, map){
                                        menu.firstChild.style.marginTop = map(easing.accel_b(t), -height, 0, !show) + "px";
                                        self.args.opacity > 0 && opacity(menu, map(easing.accel_b(t), 0, 0.85, !show));
                                }, onStop: function(){
                                        self.args.opacity > 0 && opacity(menu, 0.85);
                                        menu.firstChild.style.marginTop = "";
                                        self._menuAnim = null;
                                        if (!show) {
                                                display(menu, false);
                                                // need to remove move the
                                                // focus from the input field,
                                                // otherwise FF doesn't catch
                                                // keypress anymore.  WTF.
                                                if (self.focused)
                                                        self.focus();
                                        }
                                }
                        });
                }
        };

        function _onMouseBtn(down, ev) {
                ev = ev || window.event;
                var el = getEl(ev);
                if (el && !el.getAttribute("disabled")) {
                        var nav = el.getAttribute("dyc-btn"),
                                type = el.getAttribute("dyc-type"),
                                date = el.getAttribute("dyc-date"),
                                sel = this.selection,
                                timer,
                                captures = {
                                        mouseover : stopEvent,
                                        // mouseout  : stopEvent,
                                        mousemove : stopEvent,
                                        mouseup   : function(ev) {
                                                var cls = el.getAttribute("dyc-cls");
                                                if (cls)
                                                        delClass(el, dycCls(cls, 1));
                                                clearTimeout(timer);
                                                removeEvent(document, captures, true);
                                                captures = null;
                                        }
                                };
                        if (down) {
                                // mousedown here
                                setTimeout( curry(this.focus, this), 1 );
                                var cls = el.getAttribute("dyc-cls");
                                if (cls)
                                        addClass(el, dycCls(cls, 1));
                                if ("menu" == nav) {
                                        this.toggleMenu();
                                } else if (el && /^[+-][MY]$/.test(nav)) {
                                        if (_navClick(this, nav)) {
                                                var f = curry(function(){
                                                        if (_navClick(this, nav, true)) {
                                                                timer = setTimeout(f, 40);
                                                        } else {
                                                                captures.mouseup();
                                                                _navClick(this, nav);
                                                        }
                                                }, this);
                                                timer = setTimeout(f, 350);
                                                addEvent(document, captures, true);
                                        } else
                                                captures.mouseup();
                                } else if ("year" == nav) {
                                        this.els.yearInput.focus();
                                        this.els.yearInput.select();
                                } else if (type == "time-am") {
                                        addEvent(document, captures, true);
                                } else if (/^time/.test(type)) {
                                        var f = curry(function(type){
                                                _updateTime.call(this, type);
                                                timer = setTimeout(f, 100);
                                        }, this, type);
                                        _updateTime.call(this, type);
                                        timer = setTimeout(f, 350);
                                        addEvent(document, captures, true);
                                } else {
                                        if (date && sel.type) {
                                                if (sel.type == D.SEL_MULTIPLE) {
                                                        if (ev.shiftKey && this._selRangeStart)
                                                                sel.selectRange(this._selRangeStart, date);
                                                        else {
                                                                if (!ev.ctrlKey && !sel.isSelected(date))
                                                                        sel.clear(true);
                                                                sel.set(date, true);
                                                                this._selRangeStart = date;
                                                        }
                                                } else {
                                                        sel.set(date);
                                                        this.moveTo(intToDate(date), 2);
                                                }
                                                el = this._getDateDiv(date);
                                                _onMouseOver.call(this, true, { target: el });
                                        }
                                        addEvent(document, captures, true);
                                }
                                if (is_ie && captures && /dbl/i.test(ev.type))
                                        captures.mouseup();
                                if (!this.args.fixed && /^(DynarchCalendar-(topBar|bottomBar|weekend|weekNumber|menu(-sep)?))?$/.test(el.className) && !this.args.cont) {
                                        captures.mousemove = curry(_drag, this);
                                        this._mouseDiff = evPos(ev, getPos(this.els.topCont));
                                        addEvent(document, captures, true);
                                }
                        } else {
                                // mouseup here
                                if ("today" == nav) {
                                        if (!this._menuVisible && sel.type == D.SEL_SINGLE)
                                                sel.set(new Date());
                                        this.moveTo(new Date(), true);
                                        _doMenu(this, false);
                                } else if (/^m([0-9]+)/.test(nav)) {
                                        var date = new Date(this.date);
                                        date.setDate(1);
                                        date.setMonth(RegExp.$1);
                                        date.setFullYear(this._getInputYear());
                                        this.moveTo(date, true);
                                        _doMenu(this, false);
                                } else if (type == "time-am") {
                                        this.setHours(this.getHours() + 12);
                                }
                        }
                        if (!is_ie)     // dude, WTF...
                                stopEvent(ev);
                }
        };

        function _drag(ev) {
                ev = ev || window.event;
                var s = this.els.topCont.style, pos = evPos(ev, this._mouseDiff);
                s.left = pos.x + "px";
                s.top = pos.y + "px";
        };

        function getEl(ev) {
                var el = ev.target || ev.srcElement, orig = el;
                while (el && el.getAttribute && !el.getAttribute('dyc-type'))
                        el = el.parentNode;
                return el.getAttribute && el || orig;
        };

        function dycCls(cls, n) {
                return "DynarchCalendar-" + cls.split(/,/)[n];
        };

        function _onMouseOver(entered, ev) {
                ev = ev || window.event;
                var el = getEl(ev);
                if (el) {
                        var type = el.getAttribute("dyc-type");
                        if (type && !el.getAttribute("disabled")) {
                                if (!entered || !this._bodyAnim || type != "date") {
                                        var cls = el.getAttribute("dyc-cls");
                                        cls = cls ? dycCls(cls, 0) : "DynarchCalendar-hover-" + type;
                                        if (type != "date" || this.selection.type)
                                                condClass(entered, el, cls);
                                        if (type == "date") {
                                                condClass(entered, el.parentNode.parentNode, "DynarchCalendar-hover-week");
                                                this._showTooltip(el.getAttribute("dyc-date"));
                                        }
                                        if (/^time-hour/.test(type))
                                                condClass(entered, this.els.timeHour, "DynarchCalendar-hover-time");
                                        if (/^time-min/.test(type))
                                                condClass(entered, this.els.timeMinute, "DynarchCalendar-hover-time");
                                        delClass(this._getDateDiv(this._lastHoverDate), "DynarchCalendar-hover-date");
                                        this._lastHoverDate = null;
                                }
                        }
                }
                if (!entered)
                        this._showTooltip();
        };

        function _onMouseWheel(ev) {
                ev = ev || window.event;
                var el = getEl(ev);
                if (el ) {
                        var nav = el.getAttribute('dyc-btn'), type = el.getAttribute('dyc-type'), delta = ev.wheelDelta ? ev.wheelDelta / 120 : -ev.detail / 3;
                        delta = delta < 0 ? -1 : delta > 0 ? 1 : 0;
                        if (this.args.reverseWheel)
                                delta = -delta;
                        if (/^(time-(hour|min))/.test(type)) {
                                switch (RegExp.$1) {
                                case "time-hour": this.setHours(this.getHours() + delta); break;
                                case "time-min": this.setMinutes(this.getMinutes() + this.args.minuteStep * delta); break;
                                }
                                stopEvent(ev);
                        } else {
                                if (/Y/i.test(nav))
                                        delta *= 2;
                                _navClick(this, -delta);
                                stopEvent(ev);
                        }
                }
        };

        function _onSelChange() {
                this.refresh();
                var input = this.inputField, sel = this.selection;
                if (input) {
                        var val = sel.print(this.dateFormat);
                        (/input|textarea/i.test(input.tagName))
                                ? input.value = val
                                : input.innerHTML = val;
                }
                this.callHooks("onSelect", this, sel);
        };

        var CTRL_NAV_KEYS = {
                37: -1,
                38: -2,
                39: 1,
                40: 2
        }, NAV_KEYS = {
                33: -1,
                34: 1
        };

        function _onKeyPress(ev) {
                if (this._menuAnim)
                        return;
                ev = ev || window.event;
                var el = ev.target || ev.srcElement,
                        what = el.getAttribute('dyc-btn'),
                        key = ev.keyCode,
                        chr = ev.charCode || key,
                        nav = CTRL_NAV_KEYS[key];
                if ("year" == what && key == 13) {
                        var date = new Date(this.date);
                        date.setDate(1);
                        date.setFullYear(this._getInputYear());
                        this.moveTo(date, true);
                        _doMenu(this, false);
                        return stopEvent(ev);
                }
                if (this._menuVisible) {
                        if (key == 27 /* ESC */) {
                                _doMenu(this, false);
                                return stopEvent(ev);
                        }
                } else {
                        if (!ev.ctrlKey)
                                nav = null;
                        if (nav == null && !ev.ctrlKey)
                                nav = NAV_KEYS[key];
                        if (key == 36 /* HOME */)
                                nav = 0;
                        if (nav != null) {
                                _navClick(this, nav);
                                return stopEvent(ev);
                        }
                        chr = String.fromCharCode(chr).toLowerCase();
                        var yi = this.els.yearInput, sel = this.selection;

                        if (chr == " ") {
                                _doMenu(this, true);
                                this.focus();
                                yi.focus();
                                yi.select();
                                return stopEvent(ev);
                        }
                        if (chr >= "0" && chr <= "9") {
                                _doMenu(this, true);
                                this.focus();
                                yi.value = chr;
                                yi.focus();
                                return stopEvent(ev);
                        }
                        var mon = _("mn"), i = ev.shiftKey ? -1 : this.date.getMonth(), k = 0, m;
                        while (++k < 12) {
                                m = mon[(i + k) % 12].toLowerCase();
                                if (m.indexOf(chr) == 0) {
                                        var date = new Date(this.date);
                                        date.setDate(1);
                                        date.setMonth((i + k) % 12);
                                        this.moveTo(date, true);
                                        return stopEvent(ev);
                                }
                        }
                        if (key >= 37 && key <= 40) {
                                var date = this._lastHoverDate;
                                if (!date && !sel.isEmpty()) {
                                        date = key < 39
                                                ? sel.getFirstDate()
                                                : sel.getLastDate();
                                        if (date < this._firstDateVisible || date > this._lastDateVisible)
                                                date = null;
                                }
                                if (!date) {
                                        date = key < 39
                                                ? this._lastDateVisible
                                                : this._firstDateVisible;
                                } else {
                                        var origDate = date;
                                        date = intToDate(date);
                                        var i = 100;
                                        while (i-- > 0) {
                                                switch (key) {
                                                    case 37: // left
                                                        date.setDate(date.getDate() - 1);
                                                        break;
                                                    case 38: // up
                                                        date.setDate(date.getDate() - 7);
                                                        break;
                                                    case 39: // right
                                                        date.setDate(date.getDate() + 1);
                                                        break;
                                                    case 40: // down
                                                        date.setDate(date.getDate() + 7);
                                                        break;
                                                }
                                                if (!this.isDisabled(date))
                                                        break;
                                        }
                                        date = dateToInt(date);
                                        if (date < this._firstDateVisible || date > this._lastDateVisible)
                                                this.moveTo(date);
                                                // return stopEvent(ev);
                                }
                                delClass(this._getDateDiv(origDate), addClass(this._getDateDiv(date), "DynarchCalendar-hover-date"));
                                this._lastHoverDate = date;
                                return stopEvent(ev);
                        }
                        if (key == 13) {
                                if (this._lastHoverDate) {
                                        if (sel.type == D.SEL_MULTIPLE && (ev.shiftKey || ev.ctrlKey)) {
                                                if (ev.shiftKey && this._selRangeStart) {
                                                        sel.clear(true);
                                                        sel.selectRange(this._selRangeStart, this._lastHoverDate);
                                                }
                                                if (ev.ctrlKey)
                                                        sel.set(this._selRangeStart = this._lastHoverDate, true);
                                        } else
                                                sel.reset(this._selRangeStart = this._lastHoverDate);
                                        return stopEvent(ev);
                                }
                        }
                        if (key == 27 && !this.args.cont) {
                                this.hide();
                        }
                }
        };

        P._getDateDiv = function(date) {
                var el = null;
                if (date) try {
                                walk(this.els.body, function(e){
                                        if (e.getAttribute("dyc-date") == date)
                                                throw el = e;
                                });
                        } catch(ex) {};
                return el;
        };

        function formatString(txt, prop) {
                return txt.replace(/\$\{([^:\}]+)(:[^\}]+)?\}/g, function(str, p1, p2) {
                        var n = prop[p1], p;
                        if (p2) {
                                p = p2.substr(1).split(/\s*\|\s*/);
                                n = (n >= p.length ? p[p.length-1] : p[n]).replace(/##?/g, function(s){
                                        return s.length == 2 ? "#" : n;
                                });
                        }
                        return n;
                });
        };

        function _(id, prop) {
                var txt = I18N.__.data[id];
                if (prop && typeof txt == "string")
                        txt = formatString(txt, prop);
                return txt;
        };

        /* -----[ Calendar.Selection object ]----- */

        (D.Selection = function(sel, type, onChange, cal) {

                this.type = type;
                this.sel = sel instanceof Array ? sel : [ sel ];
                this.onChange = curry(onChange, cal);
                this.cal = cal;

        }).prototype = {

                get: function() { return this.type == D.SEL_SINGLE ? this.sel[0] : this.sel },

                isEmpty: function() { return this.sel.length == 0 },

                set: function(date, toggle, noHooks) {
                        var single = this.type == D.SEL_SINGLE;
                        if (date instanceof Array) {
                                this.sel = date;
                                this.normalize();
                                if (!noHooks)
                                        this.onChange(this);
                        } else {
                                date = dateToInt(date);
                                if (single || !this.isSelected(date)) {
                                        single
                                                ? this.sel = [ date ]
                                                : this.sel.splice(this.findInsertPos(date), 0, date);
                                        this.normalize();
                                        if (!noHooks)
                                                this.onChange(this);
                                } else if (toggle) {
                                        this.unselect(date, noHooks);
                                }
                        }
                },

                reset: function() {
                        this.sel = [];
                        this.set.apply(this, arguments);
                },

                countDays: function() {
                        var d = 0, a = this.sel, i = a.length, r, d1, d2;
                        while (--i >= 0) {
                                r = a[i];
                                if (r instanceof Array) {
                                        d1 = intToDate(r[0]);
                                        d2 = intToDate(r[1]);
                                        d += Math.round(Math.abs(d2.getTime() - d1.getTime()) / 864e5);
                                }
                                ++d;
                        }
                        return d;
                },

                unselect: function(date, noHooks) {
                        date = dateToInt(date);
                        var changed = false;
                        for (var a = this.sel, i = a.length, r; --i >= 0;) {
                                r = a[i];
                                if (r instanceof Array) {
                                        if (date >= r[0] && date <= r[1]) {
                                                var tmp = intToDate(date), d = tmp.getDate();
                                                if (date == r[0]) {
                                                        tmp.setDate(d + 1);
                                                        r[0] = dateToInt(tmp);
                                                        changed = true;
                                                } else if (date == r[1]) {
                                                        tmp.setDate(d - 1);
                                                        r[1] = dateToInt(tmp);
                                                        changed = true;
                                                } else {
                                                        var next = new Date(tmp);
                                                        next.setDate(d + 1);
                                                        tmp.setDate(d - 1);
                                                        a.splice(i + 1, 0, [ dateToInt(next), r[1] ]);
                                                        r[1] = dateToInt(tmp);
                                                        changed = true;
                                                }
                                        }
                                } else if (date == r) {
                                        a.splice(i, 1);
                                        changed = true;
                                }
                        }
                        if (changed) {
                                this.normalize();
                                if (!noHooks)
                                        this.onChange(this);
                        }
                },

                normalize: function() {
                        this.sel = this.sel.sort(function(a, b){
                                if (a instanceof Array)
                                        a = a[0];
                                if (b instanceof Array)
                                        b = b[0];
                                return a - b;
                        });
                        for (var a = this.sel, i = a.length, r, prev; --i >= 0;) {
                                r = a[i];
                                if (r instanceof Array) {
                                        if (r[0] > r[1]) {
                                                a.splice(i, 1);
                                                continue;
                                        }
                                        if (r[0] == r[1])
                                                r = a[i] = r[0];
                                }
                                if (prev) {
                                        var d2 = prev, d1 = r instanceof Array ? r[1] : r;
                                        d1 = intToDate(d1);
                                        d1.setDate(d1.getDate() + 1);
                                        d1 = dateToInt(d1);
                                        if (d1 >= d2) {
                                                var r2 = a[i + 1];
                                                if (r instanceof Array && r2 instanceof Array) {
                                                        r[1] = r2[1];
                                                        a.splice(i + 1, 1);
                                                } else if (r instanceof Array) {
                                                        r[1] = prev;
                                                        a.splice(i + 1, 1);
                                                } else if (r2 instanceof Array) {
                                                        r2[0] = r;
                                                        a.splice(i, 1);
                                                } else {
                                                        a[i] = [ r, r2 ];
                                                        a.splice(i + 1, 1);
                                                }
                                        }
                                }
                                prev = r instanceof Array ? r[0] : r;
                        }
                },

                findInsertPos: function(date) {
                        for (var a = this.sel, i = a.length, r; --i >= 0;) {
                                r = a[i];
                                if (r instanceof Array)
                                        r = r[0];
                                if (r <= date)
                                        break;
                        }
                        return i + 1;
                },

                clear: function(nohooks) {
                        this.sel = [];
                        if (!nohooks)
                                this.onChange(this);
                },

                selectRange: function(d1, d2) {
                        d1 = dateToInt(d1);
                        d2 = dateToInt(d2);
                        if (d1 > d2) {
                                var tmp = d1;
                                d1 = d2;
                                d2 = tmp;
                        }
                        var check = this.cal.args.checkRange;
                        if (!check)
                                return this._do_selectRange(d1, d2);
                        try {
                                foreach(new D.Selection([[ d1, d2 ]], D.SEL_MULTIPLE, Noop).getDates(), curry(function(date){
                                        if (this.isDisabled(date)) {
                                                if (check instanceof Function)
                                                        check(date, this);
                                                throw "OUT";
                                        }
                                }, this.cal));
                                this._do_selectRange(d1, d2);
                        } catch(ex) {}
                },

                _do_selectRange: function(d1, d2) {
                        this.sel.push([ d1, d2 ]);
                        this.normalize();
                        this.onChange(this);
                },

                isSelected: function(date) {
                        for (var i = this.sel.length, r; --i >= 0;) {
                                r = this.sel[i];
                                if (r instanceof Array && date >= r[0] && date <= r[1] || date == r)
                                        return true;
                        }
                        return false;
                },

                getFirstDate: function() {
                        var d = this.sel[0];
                        if (d && d instanceof Array)
                                d = d[0];
                        return d;
                },

                getLastDate: function() {
                        if (this.sel.length > 0) {
                                var d = this.sel[this.sel.length - 1];
                                if (d && d instanceof Array)
                                        d = d[1];
                                return d;
                        }
                },

                print: function(fmt, sep) {
                        var a = [], i = 0, r, h = this.cal.getHours(), m = this.cal.getMinutes();
                        if (!sep)
                                sep = " -> ";
                        while (i < this.sel.length) {
                                r = this.sel[i++];
                                if (r instanceof Array)
                                        a.push(printDate(intToDate(r[0], h, m), fmt) + sep +
                                               printDate(intToDate(r[1], h, m), fmt));
                                else
                                        a.push(printDate(intToDate(r, h, m), fmt));
                        }
                        return a;
                },

                getDates: function(fmt) {
                        var a = [], i = 0, d, r;
                        while (i < this.sel.length) {
                                r = this.sel[i++];
                                if (r instanceof Array) {
                                        d = intToDate(r[0]);
                                        r = r[1];
                                        while (dateToInt(d) < r) {
                                                a.push(fmt ? printDate(d, fmt) : new Date(d));
                                                d.setDate(d.getDate() + 1);
                                        }
                                } else
                                        d = intToDate(r);
                                a.push(fmt ? printDate(d, fmt) : d);
                        }
                        return a;
                }

        };

        /* -----[ calendar utilities ]----- */

        function getWeekNumber(d) {
                d = new Date(d.getFullYear(), d.getMonth(), d.getDate(), 12, 0, 0);
                var dow = d.getDay();
                d.setDate(d.getDate() - (dow + 6) % 7 + 3); // Nearest Thu
                var ms = d.valueOf(); // GMT
                d.setMonth(0);
                d.setDate(4); // Thu in Week 1
                return Math.round((ms - d.valueOf()) / (7 * 864e5)) + 1;
        };

        function getDayOfYear(now) {
                now = new Date(now.getFullYear(), now.getMonth(), now.getDate(), 12, 0, 0);
                var then = new Date(now.getFullYear(), 0, 1, 12, 0, 0);
                var time = now - then;
                return Math.floor(time / 86400000);
        };

        function dateToInt(date) {
                if (date instanceof Date)
                        return 10000 * date.getFullYear() + 100 * (date.getMonth() + 1) + date.getDate();
                if (typeof date == "string")
                        return parseInt(date, 10);
                return date;
        };

        function intToDate(date, hh, mm, ss, ms) {
                if (!(date instanceof Date)) {
                        date = parseInt(date, 10);
                        var y = Math.floor(date / 10000);
                        date = date % 10000;
                        var m = Math.floor(date / 100);
                        date = date % 100;
                        date = new Date(y, m - 1, date,
                                        hh == null ? 12 : hh,
                                        mm == null ? 0 : mm,
                                        ss == null ? 0 : ss,
                                        ms == null ? 0 : ms);
                }
                return date;
        };

        function compareDates(a, b, noDate) {
                var     y1 = a.getFullYear(), m1 = a.getMonth(), d1 = a.getDate(),
                        y2 = b.getFullYear(), m2 = b.getMonth(), d2 = b.getDate();
                return    y1 < y2 ? -3
                        : y1 > y2 ? 3
                        : m1 < m2 ? -2
                        : m1 > m2 ? 2
                        : noDate ? 0
                        : d1 < d2 ? -1
                        : d1 > d2 ? 1
                        : 0;
        };

        function printDate(date, str) {
                var     m   = date.getMonth(),
                        d   = date.getDate(),
                        y   = date.getFullYear(),
                        wn  = getWeekNumber(date),
                        w   = date.getDay(),
                        hr  = date.getHours(),
                        pm  = (hr >= 12),
                        ir  = (pm) ? (hr - 12) : hr,
                        dy  = getDayOfYear(date),
                        min = date.getMinutes(),
                        sec = date.getSeconds(),
                        re  = /%./g,
                        s;
                if (ir === 0) ir = 12;
                s = {
                        "%a": _("sdn")[w]                                              , // abbreviated weekday name
                        "%A": _("dn")[w]                                               , // full weekday name
                        "%b": _("smn")[m]                                              , // abbreviated month name
                        "%B": _("mn")[m]                                               , // full month name
                        "%C": 1 + Math.floor(y / 100)                                  , // the century number
                        "%d": d < 10 ? "0" + d : d                                     , // the day of the month (range 01 to 31)
                        "%e": d                                                        , // the day of the month (range 1 to 31)
                        "%H": hr < 10 ? "0" + hr : hr                                  , // hour, range 00 to 23 (24h format)
                        "%I": ir < 10 ? "0" + ir : ir                                  , // hour, range 01 to 12 (12h format)
                        "%j": dy < 10 ? "00" + dy : dy < 100 ? "0" + dy : dy           , // day of the year (range 001 to 366)
                        "%k": hr                                                       , // hour, range 0 to 23 (24h format)
                        "%l": ir                                                       , // hour, range 1 to 12 (12h format)
                        "%m": m < 9 ? "0" + (1 + m) : 1 + m                            , // month, range 01 to 12
                        "%o": 1 + m                                                    , // month, range 1 to 12
                        "%M": min < 10 ? "0" + min : min                               , // minute, range 00 to 59
                        "%n": "\n"                                                     , // a newline character
                        "%p": pm ? "PM" : "AM"                                         , // PM or AM
                        "%P": pm ? "pm" : "am"                                         , // pm or am
                        "%s": Math.floor(date.getTime() / 1000)                        , // UNIX time
                        "%S": sec < 10 ? "0" + sec : sec                               , // seconds, range 00 to 59
                        "%t": "\t"                                                     , // a tab character
                        "%U": wn < 10 ? "0" + wn : wn                                  , // week number
                        "%W": wn < 10 ? "0" + wn : wn                                  , // week number
                        "%V": wn < 10 ? "0" + wn : wn                                  , // week number
                        "%u": w + 1                                                    , // the day of the week (range 1 to 7, 1 = MON)
                        "%w": w                                                        , // the day of the week (range 0 to 6, 0 = SUN)
                        "%y": ("" + y).substr(2, 2)                                    , // year without the century (range 00 to 99)
                        "%Y": y                                                        , // year with the century
                        "%%": "%"                                                        // a literal '%' character
                };
                return str.replace(re, function(par) { return s.hasOwnProperty(par) ? s[par] : par });
        };

        function readDate(date) {
                if (date) {
                        if (typeof date == "number")
                                return intToDate(date);
                        if (!(date instanceof Date)) {
                                var a = date.split(/-/);
                                return new Date(parseInt(a[0], 10),
                                                parseInt(a[1], 10) - 1,
                                                parseInt(a[2], 10), 12, 0, 0, 0);
                        }
                }
                return date;
        };

        // from DynarchLIB
        function findMonth(str) {
                if (/\S/.test(str)) {
                        str = str.toLowerCase();
                        function f(a) {
                                for (var i = a.length; --i >= 0;)
                                        if (a[i].toLowerCase().indexOf(str) == 0)
                                                return i + 1;
                        };
                        return f(_("smn")) || f(_("mn"));
                }
        };

        D.isUnicodeLetter = function(ch) {
                // this does the job for many languages.
                // but if you need to use various asian languages, i.e. Chinese, you should load unicode-letter.js
                return ch.toUpperCase() != ch.toLowerCase();
        };

        // from DynarchLIB
        D.parseDate = function(data, monthFirst, today) {

                if (!/\S/.test(data))
                        return "";

                data = data.replace(/^\s+/, "").replace(/\s+$/, "");

                today = today || new Date();
                var yr = null, mo = null, da = null, h = null, m = null, s = null;

                // deal with time first

                var b = data.match(/([0-9]{1,2}):([0-9]{1,2})(:[0-9]{1,2})?\s*(am|pm)?/i);
                if (b) {
                        h = parseInt(b[1], 10);
                        m = parseInt(b[2], 10);
                        s = b[3] ? parseInt(b[3].substr(1), 10) : 0;
                        data = data.substring(0, b.index) + data.substr(b.index + b[0].length);
                        if (b[4]) {
                                if (b[4].toLowerCase() == "pm" && h < 12)
                                        h += 12;
                                else if (b[4].toLowerCase() == "am" && h >= 12)
                                        h -= 12;
                        }
                }

                var a = (function(){
                        var i = 0, ret = [], ch;
                        function next() {
                                return data.charAt(i++);
                        }
                        function peek() {
                                return data.charAt(i);
                        }
                        var isLetter = D.isUnicodeLetter;
                        function readWord(w) {
                                while (peek() && isLetter(peek())) w += next();
                                return w;
                        }
                        function readInt() {
                                var n = "";
                                while (peek() && /[0-9]/.test(peek())) n += next();
                                if (isLetter(peek())) return readWord(n);
                                return parseInt(n, 10);
                        }
                        function add(token) { ret.push(token) };
                        while (i < data.length) {
                                ch = peek();
                                if (isLetter(ch)) add(readWord(""));
                                else if (/[0-9]/.test(ch)) add(readInt());
                                else next();
                        }
                        return ret;
                })();
                var mod = [];
                for (var i = 0; i < a.length; ++i) {
                        var v = a[i];
                        if (/^[0-9]{4}$/.test(v)) {
                                yr = parseInt(v, 10);
                                if (mo == null && da == null && monthFirst == null)
                                        monthFirst = true;
                        } else if (/^[0-9]{1,2}$/.test(v)) {
                                v = parseInt(v, 10);
                                if (v >= 60)
                                        yr = v;
                                else if (v >= 0 && v <= 12)
                                        mod.push(v);
                                else if (v >= 1 && v <= 31)
                                        da = v;
                        } else if (mo == null) {
                                mo = findMonth(v);
                        }
                };

                if (mod.length >= 2) {
                        // quite nasty
                        if (monthFirst) {
                                if (mo == null)
                                        mo = mod.shift();
                                if (da == null)
                                        da = mod.shift();
                        } else {
                                if (da == null)
                                        da = mod.shift();
                                if (mo == null)
                                        mo = mod.shift();
                        }
                } else if (mod.length == 1) {
                        if (da == null)
                                da = mod.shift();
                        else if (mo == null)
                                mo = mod.shift();
                }

                if (yr == null)
                        yr = mod.length > 0 ? mod.shift() : today.getFullYear();

                if (yr < 30)
                        yr += 2000;
                else if (yr < 99)
                        yr += 1900;

                if (mo == null)
                        mo = today.getMonth() + 1;

                return yr != null && mo != null && da != null ? new Date(yr, mo - 1, da, h, m, s) : null;
        };

        /* -----[ utility library ]----- */

        // short version of DynarchLIB's Function.prototype.setDefaults
        function DEF(a, d, i, r) {
                r = {};
                for (i in d) if (d.hasOwnProperty(i)) r[i] = d[i];
                for (i in a) if (a.hasOwnProperty(i)) r[i] = a[i];
                return r;
        };

        function addEvent(el, evname, func, capture) {
                if (el instanceof Array)
                        for (var i = el.length; --i >= 0;)
                                addEvent(el[i], evname, func, capture);
                else if (typeof evname == "object")
                        for (var i in evname) {
                                if (evname.hasOwnProperty(i))
                                        addEvent(el, i, evname[i], func);
                        }
                else if (el.addEventListener)
                        el.addEventListener(evname, func, is_ie ? true : !!capture);
                else if (el.attachEvent)
                        el.attachEvent("on" + evname, func);
                else
                        el["on" + evname] = func;
        };

        function removeEvent(el, evname, func, capture) {
                if (el instanceof Array)
                        for (var i = el.length; --i >= 0;)
                                removeEvent(el[i], evname, func);
                else if (typeof evname == "object")
                        for (var i in evname) {
                                if (evname.hasOwnProperty(i))
                                        removeEvent(el, i, evname[i], func);
                        }
                else if (el.removeEventListener)
                        el.removeEventListener(evname, func, is_ie ? true : !!capture);
                else if (el.detachEvent)
                        el.detachEvent("on" + evname, func);
                else
                        el["on" + evname] = null;
        };

        function stopEvent(ev) {
                ev = ev || window.event;
                if (is_ie) {
                        ev.cancelBubble = true;
                        ev.returnValue = false;
                } else {
                        ev.preventDefault();
                        ev.stopPropagation();
                }
                return false;
        };

        function delClass(el, className, addClass) {
                if (el) {
                        var cls = el.className.replace(/^\s+|\s+$/, "").split(/\x20/), ar = [], i;
                        for (i = cls.length; i > 0;)
                                if (cls[--i] != className)
                                        ar.push(cls[i]);
                        if (addClass)
                                ar.push(addClass);
                        el.className = ar.join(" ");
                }
                return addClass;
        };

        function addClass(el, className) { return delClass(el, className, className) };

        function condClass(cond, el, className) {
                if (el instanceof Array) {
                        for (var i = el.length; --i >= 0;)
                                condClass(cond, el[i], className);
                } else
                        delClass(el, className, cond ? className : null);
                return cond;
        };

        function CE(type, cls, parent) {
                var el = null;
                if (document.createElementNS)
                        el = document.createElementNS("http://www.w3.org/1999/xhtml", type);
                else
                        el = document.createElement(type);
                if (cls)
                        el.className = cls;
                if (parent)
                        parent.appendChild(el);
                return el;
        };

        function Array$(obj, start) {
                if (start == null)
                        start = 0;
                var a, i, j;
                try {
                        a = Array.prototype.slice.call(obj, start);
                } catch (ex) {
                        a = new Array(obj.length - start);
                        for (i = start, j = 0; i < obj.length; ++i, ++j)
                                a[j] = obj[i];
                }
                return a;
        };

        // see Function.prototype.closure (or the '$' shortcut) in DynarchLIB
        // http://www.dynarchlib.com/dl/index.html#api://Function.xml:type=object_method:func=closure
        function curry(f, obj) {
                var args = Array$(arguments, 2);
                return ( obj == undefined
                         ? function() { return f.apply(this, args.concat(Array$(arguments))) }
                         : function() { return f.apply(obj, args.concat(Array$(arguments))) } );
        };

        function walk(el, f) {
                if (!f(el))
                        for (var i = el.firstChild; i; i = i.nextSibling)
                                if (i.nodeType == 1)
                                        walk(i, f);
        };

        // from DynarchLIB, but this is a very short version!
        function animation(args, timer, i) {
                args = DEF(args, { fps      : 50,
                                   len      : 15,
                                   onUpdate : Noop,
                                   onStop   : Noop });
                if (is_ie) {
                        // args.fps = Math.round(args.fps / 2);
                        args.len = Math.round(args.len / 2); // IE is very slow, shorten animations
                }
                function map(t, a, b, rev) {
                        return rev ? b + t * (a - b) : a + t * (b - a);
                };
                function start() {
                        if (timer)
                                stop();
                        i = 0;
                        timer = setInterval(update, 1000 / args.fps);
                };
                function stop() {
                        if (timer) {
                                clearInterval(timer);
                                timer = null;
                        }
                        args.onStop(i / args.len, map);
                };
                function update() {
                        var n = args.len;
                        args.onUpdate(i / n, map);
                        if (i == n)
                                stop();
                        ++i;
                };
                start();
                return { start  : start,
                         stop   : stop,
                         update : update,
                         args   : args,
                         map    : map };
        };

        // from DynarchLIB!
        var easing = {
                elastic_b : function(x) { return 1 - Math.cos(-x * 5.5 * Math.PI)/Math.pow(2, 7 * x) },
                magnetic  : function(x) { return 1 - Math.cos(x * x * x * 10.5 * Math.PI)/Math.exp(4 * x) },
                accel_b   : function(x) { x = 1 - x; return 1 - x * x * x * x },
                accel_a   : function(x) { return x * x * x },
                accel_ab  : function(x) { x = 1 - x; return 1 - Math.sin(x * x * Math.PI / 2) },

                accel_ab2 : function(x) {
                        return (x /= 0.5) < 1
                                ? 1/2 * x * x
                                : -1/2 * ((--x)*(x-2) - 1);
                },

                brakes    : function(x) { x = 1 - x; return 1 - Math.sin(x * x * Math.PI) },
                shake     : function(t) { return t < 0.5 ? -Math.cos(t*11*Math.PI)*t*t : (t = 1-t, Math.cos(t*11*Math.PI)*t*t) }
        };

        function opacity(el, o) {
                if (o === "")
                        is_ie
                                ? el.style.filter = ""
                                : el.style.opacity = "";
                else {
                        if (o != null)
                                is_ie
                                        ? el.style.filter = "alpha(opacity=" + o * 100 + ")"
                                        : el.style.opacity = o;
                        else if (!is_ie)
                                o = parseFloat(el.style.opacity);
                        else if (/alpha\(opacity=([0-9.])+\)/.test(el.style.opacity))
                                o = parseFloat(RegExp.$1) / 100;
                }
                return o;
	};

        function display(el, v) {
                var s = el.style;
                if (v != null)
                        s.display = v ? "" : "none";
                return s.display != "none";
        };

        function evPos(ev, d) {
                var x = is_ie ? ev.clientX + document.body.scrollLeft : ev.pageX;
                var y = is_ie ? ev.clientY + document.body.scrollTop : ev.pageY;
                if (d) {
                        x -= d.x;
                        y -= d.y;
                }
                return { x: x, y: y };
        };

        function getPos(el) {
                // if (el.getBoundingClientRect) {
                //         var box = el.getBoundingClientRect();
                //         return { x: box.left - document.documentElement.clientLeft,
                //                  y: box.top - document.documentElement.clientTop };
                // } else if (document.getBoxObjectFor) {
                //         var box = el.ownerDocument.getBoxObjectFor(el);
                //         var pos = { x: box.x, y: box.y };
                //         // is this a bug or what?  we have to substract scroll values manually!
                //         while (el.parentNode && el.parentNode !== document.body) {
                //                 el = el.parentNode;
                //                 pos.x -= el.scrollLeft;
                //                 pos.y -= el.scrollTop;
                //         }
                //         return pos;
                // }
                // other browsers do the hard way
                // if (/^body$/i.test(el.tagName))
                //         return { x: 0, y: 0 };
                var
                        SL = 0, ST = 0,
                        is_div = /^div$/i.test(el.tagName),
                        r, tmp;
                if (is_div && el.scrollLeft)
                        SL = el.scrollLeft;
                if (is_div && el.scrollTop)
                        ST = el.scrollTop;
                r = { x: el.offsetLeft - SL, y: el.offsetTop - ST };
                if (el.offsetParent) {
                        tmp = getPos(el.offsetParent);
                        r.x += tmp.x;
                        r.y += tmp.y;
                }
                return r;
        };

        function getViewport() {
                var h = document.documentElement, b = document.body;
                return {
                        x: h.scrollLeft   || b.scrollLeft,
                        y: h.scrollTop    || b.scrollTop,
                        w: h.clientWidth  || window.innerWidth  || b.clientWidth,
                        h: h.clientHeight || window.innerHeight || b.clientHeight
                };
        };

        function foreach(a, f, i) {
                for (i = 0; i < a.length; ++i)
                        f(a[i]);
        };

        var Noop = new Function();

        function $(el) {
                if (typeof el == "string")
                        el = document.getElementById(el);
                return el;
        };

        return D;

})();
