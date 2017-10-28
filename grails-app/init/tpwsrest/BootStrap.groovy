package tpwsrest

import com.mbds.tpwsrest.Book
import com.mbds.tpwsrest.Library

class BootStrap {

    def init = { servletContext ->
        def lib1 = new Library(name: "Lib de Tom",address:"444 Chemin de Chambarot", yearCreated: 1991).save(flush:true, failOnError:true)
        lib1.addToBooks(new Book(name: "Troll de Troy", isbn: "x0000", author: "Soleil", releaseDate: new Date())).save(flush:true, failOnError:true)
    }
    def destroy = {
    }
}
