# Frontend Challenge

## Scope
The assignment is extremely open and the scope must be carefully considered
otherwise I'm going to lose my whole week to this. (I ended up losing a week+
on this anyways because I ended up trying Tailwind CSS, shadow-cljs, and 
cypress.)

- Port main functionality to CLJS
- Demonstrate a few clear UI improvements

(Looking back, doing both is too much. It's one thing to do basic functionality,
it's another to actually have a nice UI and ok design. Design and UI aspects
are extremely incremental and can be very detailed to the point someone with
a specific kind of OCD might excel.)

## Technology
### re-frame
A framework based on the reagent library based on react. I've used this in
previous projects and am fairly satisfied. 
### Tailwind CSS
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
to a utility class.

### Garden
You should already be familiar with this. It's pretty much clojure's
equivalent of Sass, Less, etc.
### shadow-cljs
I gave this a shot after it was mentioned in our interview. It's great and I never
looked at it before because the author doesn't sell it well. The killer feature
here is that NPM libraries just work. Prior to this we had:

- cljsjs, packages often woefully outdated and not in working condition
- manual packaging, we had to write hand written externs and deal with dependencies
- lein `:npm-deps` option which sometimes works for some libraries

## Architecture
This project uses Roomkey's API via proxy. I had to add a header to allow CORS.
The site is done as a SPA using re-frame with a MVC structure. There are some
differences from a more traditional backend MVC structure.

- During routing we also dispatch for the necessary data for a given page via API
  in addition to dispatching for the associated view.
- When a user navigates links, they user does not make a request for the URL. The
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

### UI

- There's no color scheme to speak of. The logo is black and white and it looks
like the other chosen colors could very well be defaults from bootstrap.
- There's a lack of photos and illustrations. The one on the frontpage looks like
it could be a stock photo or a default hero image from Wordpress.
- The product's value proposition must be made very clear. We can give you the
very best rate because WE ARE THE HOTELS.
- Everything needs to be more customer centric. Design decisions should stem
from the customer. Examples on the front page: "Search not Sales". What does
that even mean? My eyes glaze over and don't even read the thing following it,
I don't care. "Set Price Alerts". That's kind of cool but that's a feature and
not the product. I'd leave it on the front page but give it less weight.
"Book Directly" should be given more importance and elucidated on.
- Sign up to our mailing list. Every site wants my email. You'll need to give
me a good reason.
- Scout. I actually like Scout. Who did the design? I'd definitely leave it on
the front page and elucidate on it further. 

- There's a lot of small UI things that differ from other sites and
are arguably worse. An example is the currency selector. If for example, I want
Ukrainian Hyrvnia, I'd have to scroll down pretty far. Other sites do a modal
or whatever that let's the user see ALL the currencies at once. Check-in and
check-out dates weirdly have Rooms and Guests in the drop down as well. I click
to input dates, why is this unrelated stuff bunched in as well?

I'll leave it at that because I already feel like I'm going out of scope. I am
not a designer but I can discuss and implement things from a designer.

## Testing
TODO. Generally I don't write tests during a project's early stages. This is
because the work is very exploratory and things are subject to extreme change.
With tests, every modification could necessitate twice the amount of code to
be written as compared to without. Tests become important when there are many
users or when the requirements are very well specified.

## Logging
TODO.

## Production Deployment
### Cross browser CSS
We use PostCSS's autoprefixer before we deploy. This fixes many CSS issues but
not all of them. The site needs to be eyeballed on different browsers to look
for obvious broken styling.
