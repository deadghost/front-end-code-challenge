# Room Key Front End Development Challenge

Greetings and salutations!

Thanks for your interest in joining the team here at Room Key. Part of our
evaluation process is to set an open-ended challenge which will allow you to
flex your creative muscles and give you an opportunity to demonstrate your
coding skills. We think (and hope you agree) that this is better than asking
you to scribble algorithms on a whiteboard in an interview setting.

We realize that this represents a significant investment of effort, but one we
hope will be worthwhile for both you and us. Even if your application to Room
Key is ultimately unsuccessful, you are more than welcome to use what you
produce for this challenge in your personal portfolio.

If you are successful then we'll invite you to our offices for a face-to-face
interview and will use what you produce for this challenge as a starting
point for our chat.

Cheers!  
The Room Key Team

## Scope
The assignment is extremely open and the scope must be carefully considered
otherwise I'm going to lose my whole week to this. (I ended up losing a week+
on this anyways because I ended up trying Tailwind CSS, shadow-cljs, and 
cypress.)

- Port main functionality to CLJS
- Demonstrate a few clear UI improvements

(Looking back, doing both is too much. It's one thing to do basic functionality,
it's another to actually have a nice UI and ok design. Design and UI aspects
are extremely incremental and can't be done in one shot. It also takes time
and research to understand what the business and application is trying to
achieve.)

### What I Actually Got Done

- Roomkey business research, how and why does it tick
- Roomkey application research, how the current site is done, strengths, weaknesses
- Formulate ways to emphasize strengths and de-emphasize weaknesses
- Basic re-frame SPA project setup
- Clone of Front Page and skeleton of hotels and hotel page
- Healthy README on my setup and thought processes

What I actually got done codewise is honestly not very impressive. I front loaded
time into understanding the product and why certain things are the way they are now.

### What I Didn't Get Done
A lot, it's literally a full time job.

- Mobile-friendly design
- Accessibility
- Logging
- Metrics
- Testing
- Page composition
- UI adjustments
- CSS -> Garden compiler
- Page weight/Performance
- Cross-browser compatibility
- More organized lifecycle/page-state handling
- Janky fugly bugs

## Development Setup

TODO: Simplify this into a startup script. Normally I have a `start-dev.bash`
that just does everything.

1. Start API proxy server:

    $ npm install
	$ npm start
	
2. Compile garden CSS:

    $ cd roomkey
    $ lein garden auto

3. Start shadow-cljs:

    $ npx shadow-cljs watch frontend

The entry point to the frontend can be found at `roomkey/src/cljs/roomkey/app.cljs`	

4. Start backend server:

    $ lein run
	
The entry point to the backend server can be found at 
`roomkey/src/clj/roomkey/site/server.clj`	

5. Open http://localhost:1238/ in your browser.

## Technology
### JS
#### shadow-cljs
I gave this a shot after it was mentioned in our interview. It's great and I never
looked at it before because the author doesn't sell it well. The killer feature
here is that NPM libraries just work. Prior to this we had:

- cljsjs, packages often woefully outdated and not in working condition
- manual packaging, we had to write hand written externs and deal with dependencies
- lein `:npm-deps` option which sometimes works for some libraries
#### re-frame
A framework based on the reagent library based on react. I've used this in
previous projects and am fairly satisfied. 
#### react-dates
React library written by AirBnB. I used it for the frontpage date range selector.
There are a lot of junky, poorly maintained libraries on NPM. I just picked a
date range library supported by a large organization. There's no deep underlying
reason to pick this in particular.

### CSS
#### Tailwind CSS
Tailwind is an utility based CSS library. I've been writing CSS for nearly
a decade now and it's been a struggle to write CSS that is not "write-only".
The unfortunate truth is that HTML is coupled with CSS and we are not able
to freely mix and match HTML and CSS files. I've dabbled with this approach
in my previous project and plan to use this approach going forward.

A nice long write up on utility/functional CSS:
https://adamwathan.me/css-utility-classes-and-separation-of-concerns/

This project uses tailwind via CDN in conjunction with garden. It's my first
time using Tailwind but I am enjoying it so I plan on porting it over to garden.

I like how CSS code that can be refactored sticks out because they don't belong
to a utility class. It's also nice how the styling is part of the HTML code so
most of the time we don't need to go digging in the CSS file to alter styling.

The tiny amount of CSS I wrote can be found in 
`src/clj/roomkey/site/css/app.clj`.

#### Garden
You should already be familiar with this. It's pretty much clojure's
equivalent of Sass, Less, etc.

## Architecture
This project uses Roomkey's API via proxy. I had to add a header to allow CORS.
The site is done as a SPA using re-frame with a MVC structure. There are some
differences from a more traditional backend MVC structure.

- During routing we also dispatch for the necessary data for a given page via API
  in addition to dispatching for the associated view.
- When a user navigates links, the user does not make a request for the URL. The
  user stays on the page, we change the URL, and the user's view changes. We do
  this via HTML5 pushState using the clojurescript pushy library.
  
A SPA is a good choice today as users are increasingly expecting more interactive
sites with faster feedback loops. This applies to things that were once far more
static like documentation (Stripe's is a good example).

## Design

I am not a designer. I do, however, wear the designer hat periodically for my own
projects and output acceptable work for my purposes. Based on our chat, it sounds
like you're looking for a stong frontend developer that is also very well versed
in UI/UX/design. I am able to intelligibly discuss those aspects but I am not
that person. I would want to work with a dedicated designer to discuss and
implement her design decisions. I'll give my feedback on the current design
anyways.

### UX

If you want your users to have a good experience, you should know who your users
are.

#### Customer Demographics
The frontend is the customer's first point of interaction. It's vitally important
to understand the merits of our product and who our customers are. A business
that is not customer obsessive will not fare well.

I have some understanding of this product on a customer level. I've spent four
years traveling Asia and used competitors like Booking and Agoda; however,
the following is all conjecture.

Who's using Room Key? Probably not business travelers because they can expense
everything and don't care about saving the company a few dollars. This leaves
the leisure crowd. Young people don't have much money and will often opt for
hostels or cheaper local hotels. Elderly do not seem to frequently travel for
obvious reasons. This leaves the middle age crowd.

Middle age people are often stressed by life and work and wish for more relaxed
trips. They will often have children and will need kid friendly accommodation.
They often are not incredibly tech literate and need simple messaging and
interface.

- Best price guarantee
- Started by trusted :hotel-brands

#### Customer Usage Goals
We need to establish metrics for what constitutes successful usage of our site.
In this case, we'll say the successful use of the site is when a customer books
with a hotel. This should be measured. We need to be aware of the steps required
for success and record metrics for those steps as well.

#### Usage Funnel
We break down the steps for successful usage and record the success rate for each
step. The worst dropoffs are where effort should be directed. In this case our
funnel is:

- Search for hotels at a location in a date range. This is measured by successful
search queries. It can be subdivided into form filling steps and those substeps
can be measured as well.
- Hotel browsing. We measure the number of individual hotels the user inspects.
- Hotel configuration. We measure the fields set.
- Handoff to the Hotel's site. This is measured by the number of redirects to
Hotels' sites via 'Book at ...' button.
- If possible we also measure the success rate once the user is on the hotel site.

Metrics collected can be as fine grained as we need.

#### Things I'd Like (That Others Might Not)
- Frontpage shows Popular Destinations
- Frontpage shows Low Season Destinations

Sometimes I want to be where everyone else is at. There's usually a good reason
why they're somewhere at a given time of the year. Sometimes I want to be where
no one is at, because that's where the best deals are. I also don't want to deal
with a million other tourists in some tiny location. Showing some nearby
destinations might be cool too. A tool that does this for a given date range
might also be cool (popular/bargain destinations during my vacation slot).

### UI

#### General
- There's no color scheme to speak of. The logo is black and white and it looks
like the other chosen colors could very well be defaults from bootstrap.
- There's a lack of photos and illustrations. It doesn't elicit business
credibility. 
#### Front Page
- The product's value proposition must be made very clear. We can give you the
very best rate because WE ARE THE HOTELS.
- Page elements should be considered from a customer perspective. Examples on 
the front page: "Search not Sales". What does that even mean? My eyes glaze 
over and don't even read the thing following it, I don't care. "Set Price 
Alerts". That's kind of cool but that's a feature and not the product. I'd 
leave it on the front page but give it less weight. "Book Directly" should be 
given more importance and elucidated on.
- Currency selector usability. If for example, I want Ukrainian Hyrvnia, I'd 
have to scroll down pretty far. Other sites do a modal or whatever that let's 
the user see ALL the currencies at once. 
- Field separation. Check-in and check-out dates weirdly have Rooms and Guests 
in the drop down as well. This defies my expectations and should be split into
another field.
- There's a Log in but not a Register. Unintuitively Register is hidden under
Log in.
- Hero image is not an efficient use of space. A left/right side split is probably
superior for displaying more information.
- Sign up to our mailing list. Every site wants my email. You'll need to give
me a good reason.
- Scout. I actually like Scout. Who did the design? I'd definitely leave it on
the front page and elucidate on it further. 

I'll leave it at that because I already feel like I'm going out of scope. I am
not a designer but I can discuss and implement things from a designer.

#### Hotel Results Page
I don't have as many complaints about this page and the ones I do have are
debatable.

- A map is valuable, but I don't know about half the page valuable.
- Filters and sorting a bit non-standard.
- I'd like to see: distance from city center and distance from airport
- A left sided filter might be nice.
- I hate Too Low to Show prices but I understand there are business constraints.
- Sorting by largest discount would be cool.
- Nearest hotels to a given address might be nice.

#### Hotel Page
- Listing nearby attractions would be a nice touch.
- Layout looks a bit funny to me but I'd need to take a longer look.

## Testing
TODO. Generally I don't write tests during a project's early stages. This is
because the work is very exploratory and things are subject to extreme change.
With tests, every modification could necessitate twice the amount of code to
be written as compared to without. Tests become important when there are many
users or when the requirements are very well specified.

## Logging
TODO

### Mobile/Tablet CSS
TODO

### Accessibility
TODO

## Production Deployment
TODO
### Cross browser CSS
TODO We use PostCSS's autoprefixer before we deploy. This fixes many CSS issues but
not all of them. The site needs to be eyeballed on different browsers to look
for obvious broken styling.
