<script type="text/javascript">
    $(document).ready(function () {
        $("#form").validate({
            rules: {
                password: {
                    required: true
                },
                emailAddress: {
                    required: true,
                    email: true
                }
            }
        });
    });
</script>

<!-- HOME -->
<div data-role="page" id="home">
    <header data-role="header">
        <h1>On-Time Appointment System</h1>
        <div data-role="navbar" data-position="fixed" >
            <ul>
                <li><a href="#home" class="ui-icon-home ui-btn-icon-top ui-btn-active ui-state-persist" data-theme="b" data-transition="slide">Home</a></li>
                <li><a href="#solutions" class="ui-icon-star ui-btn-icon-top" data-theme="b" data-transition="slide">Solutions</a></li>
                <li><a href="#contactus" class="ui-icon-info ui-btn-icon-top" data-theme="b" data-transition="slide">Contact Us</a></li>
            </ul>
        </div>
    </header>
    <section data-role="content">
        <div id="messages">
            <div id="postDataError" class="error smallText "></div>
            <div id="postDataSuccess" class="good smallText "></div>
        </div> <!-- messages -->
        <!-- spinner div-->
        <div id="loading"></div>
        <h2>Associate Login:</h2>
        <c:if test="${validationErrorFlag eq true}">
            <span class="error smallText">${mobileError}</span>
        </c:if>
        <form id="form" action="<c:url value='/mobile/m_associateHome' />"  method="post" data-ajax="false">
            <label for="emailAddress">Email Address:</label>
            <input type="email" name="emailAddress" id="emailAddress" value="${param.emailAddress}" required="please enter your email address">
            <label for="password">Password:</label>
            <input type="password" name="password" id="password" value="${param.password}" required="please enter your password">
            <br>
            <button class="ui-btn ui-icon-lock ui-btn-icon-left ui-shadow ui-corner-all">Login</button>
        </form>
    </section>
</div>

<!-- SOLUTIONS -->
<div data-role="page" id="solutions">
    <header data-role="header" data-position="fixed" >
        <h1>On-Time AS</h1>
        <div data-role="navbar">
            <ul>
                <li><a href="#home" class="ui-icon-home ui-btn-icon-top" data-theme="b" data-transition="slide">Home</a></li>
                <li><a href="#solutions" class="ui-icon-star ui-btn-icon-top ui-btn-active ui-state-persist"  data-theme="b" data-transition="slide">Solutions</a></li>
                <li><a href="#contactus" class="ui-icon-info ui-btn-icon-top" data-theme="b" data-transition="slide">Contact Us</a></li>
            </ul>
        </div>
    </header>

    <section data-role="content">
        <div class="content-primary">
            <div data-role="collapsible">
                <h2><img src="../img/mobileImages/logo_vprospect.gif" alt="vProspect 2.0">vProspect 2.0</h2>
                <h3>What vProspect can help you do</h3>
                <ul>
                    <li>Define your target audience and competition.</li>
                    <li>Research your competition and your target audience&rsquo;s behavior, needs, technical know-how level, etc.</li>
                    <li>Establish a conceptual and visual identity that corresponds to the defined direction of the company.</li>
                    <li>Ensure consistency across all media.</li>
                    <li>Establish corporate guidelines for cohesiveness of any future developments.</li>
                    <li>Define and/or re-defining your service and/or product offerings.</li>
                    <li>Define expansion plans and strategies.</li>
                    <li>Implement search engine optimization of all online materials.</li>
                </ul>
            </div>
            <div data-role="collapsible">
                <h2><img src="../img/mobileImages/logo_vconvert.gif" alt="vConvert 2.0">vConvert 2.0</h2>
                <h3>What vConvert can help you do</h3>
                <ul>
                    <li>Build a visual and functional user front end that focuses exclusively on providing an exceptional user experience while conveying company, service and product information.</li>
                    <li>Cause the desired emotional response in a user to facilitate conversion to a client.</li>
                    <li>Build sites that move beyond merely conveying information. They advertise, motivate, excite and inspire. Users are more likely to remember and recommend your site.</li>
                    <li>Research your target audience&rsquo;s behavior, needs, technical know-how and level, etc.</li>
                    <li>Develop any applicable back-end programs to facilitate user interaction with the company and offerings on a highly functional level to allow any kind of online transactions and a completely centralized information collection and access point, accessible from any location.</li>
                    <li>Create an architecture to display the site content and information in a logical and easy to access manner. Facilitating thereby the desired ease, for the user to find what they are looking for quickly and easily. Allowing the user to focus on the subject at hand rather than searching for information on the site.</li>
                </ul>
            </div>
            <div data-role="collapsible">
                <h2><img src="../img/mobileImages/logo_vretain.gif" alt="vRetain 1.0">vRetain 1.0</h2>
                <h3>What vRetain can help you do</h3>
                <ul>
                    <li>Streamline existing business infrastructures by utilizing technology for automation, information centralization, and information sharing among all business units and departments, as well as efficient prospect &amp; customer tracking and interaction within the company.</li>
                    <li>Collect crucial information, data and statistics that allow a company to keep in touch with users in a very customized, highly targeted way.</li>
                    <li>Analyze site visitor behavior through traffic analysis, usability studies and interactive polling. Recommending and making site and application adjustments to improve usability and user experience.</li>
                </ul>
            </div>
    </section>
</div>

<!-- SOLUTIONS : VPROSPECT -->
<div data-role="page" id="vprospect">
    <header data-role="header">
        <a href="#" data-role="button" data-icon="arrow-l" data-rel="back" data-transition="slide">Back</a>
        <h1>On-Time AS</h1>
    </header>

    <section data-role="content">
        <img src="../img/mobileImages/logo_vprospect.gif" width="84" height="48" alt="vprospect">
        <h2>vProspect 2.0</h2>
        <h3>What vProspect can help you do</h3>
        <ul>
            <li>Define your target audience and competition.</li>
            <li>Research your competition and your target audience's behavior, needs, technical know-how level, etc.</li>
            <li>Establish a conceptual and visual identity that corresponds to the defined direction of the company.</li>
            <li>Ensure consistency across all media.</li>
            <li>Establish corporate guidelines for cohesiveness of any future developments.</li>
            <li>Define and/or re-defining your service and/or product offerings.</li>
            <li>Define expansion plans and strategies.</li>
            <li>Implement Search Engine Optimization of all Online materials.</li>
        </ul>
    </section>
</div>

<!-- SOLUTIONS : VCONVERT -->
<div data-role="page" id="vconvert">
    <header data-role="header">
        <a href="#" data-role="button" data-icon="arrow-l" data-rel="back" data-transition="slide">Back</a>
        <h1>On-Time AS</h1>
    </header>

    <section data-role="content">
        <img src="../img/mobileImages/logo_vconvert.gif" width="84" height="48" alt="vconvert">
        <h2>vConvert 2.0</h2>
        <h3>What vConvert can help you do</h3>
        <ul>
            <li>Build a visual and functional user front end that focuses exclusively on providing an exceptional user experience while conveying company, service and product information.</li>
            <li>Cause the desired emotional response in a user to facilitate conversion to a client.</li>
            <li>Build sites that move beyond merely conveying information. They advertise, motivate, excite and inspire. Users are more likely to remember and recommend your site.</li>
            <li>Research your target audience's behavior, needs, technical know-how and level, etc.</li>
            <li>Develop any applicable back-end programs to facilitate user interaction with the company and offerings on a highly functional level to allow any kind of online transactions and a completely centralized information collection and access point, accessible from any location.</li>
            <li>Create an architecture to display the site content and information in a logical and easy to access manner. Facilitating thereby the desired ease, for the user to find what they are looking for quickly and easily. Allowing the user to focus on the subject at hand rather than searching for information on the site.</li>
        </ul>
    </section>
</div>

<!-- SOLUTIONS : VRETAIN -->
<div data-role="page" id="vretain">
    <header data-role="header">
        <a href="#" data-role="button" data-icon="arrow-l" data-rel="back" data-transition="slide">Back</a>
        <h1>On-Time AS</h1>
    </header>

    <section data-role="content">
        <img src="../img/mobileImages/logo_vretain.gif" width="84" height="48" alt="vretain">
        <h2>vRetain 1.0</h2>
        <h3>What vRetain can help you do</h3>
        <ul>
            <li>Streamline existing business infrastructures by utilizing technology for automation, information centralization, and information sharing among all business units and departments, as well as efficient prospect &amp; customer tracking and interaction within the company.</li>
            <li>Collect crucial information, data and statistics that allow a company to keep in touch with users in a very customized, highly targeted way.</li>
            <li>Analyze site visitor behavior through traffic analysis, usability studies and interactive polling. Recommending and making site and application adjustments to improve usability and user experience.</li>
        </ul>
    </section>
</div>

<!-- CONTACT US -->
<div data-role="page" id="contactus">
    <header data-role="header" data-position="fixed">
        <h1>On-Time AS</h1>
        <div data-role="navbar">
            <ul>
                <li><a href="#home" class="ui-icon-home ui-btn-icon-top" data-theme="b" data-transition="slide">Home</a></li>
                <li><a href="#solutions" class="ui-icon-star ui-btn-icon-top" data-theme="b" data-transition="slide">Solutions</a></li>
                <li><a href="#contactus" class="ui-icon-info ui-btn-icon-top ui-btn-active ui-state-persist" data-theme="b" data-transition="slide">Contact Us</a></li>
            </ul>
        </div>
    </header>

    <section data-role="content">
        <h3>Contact Us</h3>
        <form action="http://vectacorp.com/vc/scripts/mobile.asp" method="post" data-ajax="false">
            <label for="name">Name:</label>
            <input type="text" name="name" id="name">
            <label for="companyname">Company Name:</label>
            <input type="text" name="companyname" id="companyname">
            <label for="phone">Phone:</label>
            <input type="tel" name="phone" id="phone">
            <label for="email">Email:</label>
            <input type="email" name="email" id="email">

            <label for="size">Company Size:</label>
            <input type="range" name="size" id="size" min="10" max="500" value="10" step="10" data-highlight="true"><br>

            <fieldset data-role="controlgroup" data-type="horizontal">
                <legend>Currently installed vSolutions?</legend>
                <label><input type="radio" name="installed" id="yes">Yes</label>
                <label><input type="radio" name="installed" id="no">No</label>
            </fieldset>

            <fieldset data-role="controlgroup">
                <legend>Which solutions are you interested in?</legend>
                <input type="checkbox" name="vprospect" id="vprospect">
                <label for="vprospect">vProspect</label>
                <input type="checkbox" name="vconvert" id="vconvert">
                <label for="vconvert">vConvert</label>
                <input type="checkbox" name="vretain" id="vretain">
                <label for="vretain">vRetain</label>
            </fieldset>

            <fieldset data-role="controlgroup" data-type="horizontal">
                <legend>Current infrastructure?</legend>
                <input type="radio" name="infrastructure" id="linux">
                <label for="linux">Linux</label>
                <input type="radio" name="infrastructure" id="mac">
                <label for="mac">Mac</label>
                <input type="radio" name="infrastructure" id="windows">
                <label for="windows">Windows</label>
            </fieldset>

            <label for="hearaboutus" class="select">How did you hear about us?</label>
            <select name="hearaboutus" id="hearaboutus">
                <option value="magazine">Magazine Ad</option>
                <option value="radio">Radio Ad</option>
                <option value="tv">TV Ad</option>
                <option value="word">Word of Mouth</option>
            </select>

            <label for="questions">Questions/Comments:</label>
            <textarea name="questions" id="questions"></textarea>
            <br>
            <input type="submit" value="Submit Form">
        </form>
    </section>
</div>

<!-- RECENT TWEETS -->
<div data-role="page" id="twitter">
    <header data-role="header">
        <h1>Recent Tweets</h1>
    </header>

    <section data-role="content">
        <div id="twitterfeed"></div>
    </section>
</div>
</body>
</html>
