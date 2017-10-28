package tpwsrest

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/book"(controller: "api", action: "book")
        "/books"(controller: "api", action: "books")
        "/library"(controller: "api", action: "library")
        "/libraries"(controller: "api", action: "libraries")
        "/biblio/$id/book"(controller: "biblio", action: "book")
        "/biblio/$id/books"(controller: "biblio", action: "books")



        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
