<template>
  <section class="bg-gradient-to-b from-gray-50 to-white py-12 lg:py-16">
    <div class="mx-auto px-4 lg:px-8">
      <!-- Header Section -->
      <div class="text-center mb-12">
        <h2 class="text-3xl lg:text-4xl font-bold text-[#002855] mb-4">
          Dụng Cụ Nổi Bật
        </h2>
        <p class="text-gray-600 text-base lg:text-lg max-w-2xl mx-auto">
          Tối ưu hóa quá trình luyện tập của bạn với các dụng cụ được chuyên gia khuyên dùng.
        </p>
      </div>

      <!-- Products Grid - 12 Column Layout -->
      <div class="grid grid-cols-12 gap-6 lg:gap-8">
        <!-- Product Card 1 -->
        <div class="col-span-12 sm:col-span-6 lg:col-span-4">
          <Card class="product-card h-full hover:shadow-xl transition-shadow duration-300">
            <template #header>
              <div class="relative bg-gray-100 p-8 flex items-center justify-center h-64">
                <img
                  src="data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxAQDxAQDxAQEBAQDxUQEA8VEBAQEBYXFRIWFhUVFRYZHSggGBolGxUVITEhJSkrLi4uFx8zODMsNygtLisBCgoKDg0OFxAQGi8fHR0tLi0tLi4tLS0uKy8tLzctLy0vLi0rKzMtLSstLS0vNy0rNy0tLS0tLS0tLS0rKy0sK//AABEIAOEA4QMBIgACEQEDEQH/xAAcAAABBQEBAQAAAAAAAAAAAAAAAQQFBgcDAgj/xABAEAABAwIDBAgDBwIGAQUAAAABAAIDBBESITEFBkFRBxMiYXGBkaEyscEjQlJictHhgvAUM1OSosJjJEODs/H/xAAYAQEBAQEBAAAAAAAAAAAAAAAAAQMEAv/EAC4RAQACAgEBBgUCBwAAAAAAAAABAgMRBBIhMTJRcZFhgaGx0TPBBRMUIkFigv/aAAwDAQACEQMRAD8A2ZKhCBEJUiBEhXpIgRNqysjhYZJXtjY0XLnENaPMrltva0VHTyVE7sMcYueZJyDWji4mwAWOudV7cqC+YuZTtdaOAG7GDlb77+ZOQQXKv6U9nscWsMsttXMYMPqSPknmw9/aCrcGtmMbzo2QBoPdiBI9V42duPCxgBjZp94B599PJRm8fRvFKwuhAimHwuaMIPc4DggvxaV5cVkOwN/Zdnskp69sjzTktDRm8EG2G54fId1lXt4+kaqrS5sYfHDp1bHuaLfncLX9bdyDaNpbzUVPlNUwscPu4w5/+0XKhn9JGyxl/iHHvEUlvksGMhOrYR4kOPqAUCNp+7HfucR9Ag+g6TfjZ0uTapgP5w+P3cLKfp6lsjQ6N7XtOjmuDh6hfLwpuLS5vmCPUZe6dUO2qmjeHQyyMPNt2g+I0I7ioPp5j10a9Y9srpXmwASQxSuGpDjE4/MA+QCseyulOglOGZstM/Q4m42A/qbn7KjQQV6umlFWRzMD4pGSMOj2uDm+oTkIPYK9LldemvQe0qQJUAhCEAlQlQCEIQKiyVIgLJEpKRAJLJVH7f2kKSkqKl2kMTpLcyB2R5mw80GU9JW1n120WUEJPVUz8LraGYi73HmGNNh+YnktG3V2CylgYMPaw+n881mvRLswz1D6mXtG5cXHi4nE93m8+y2hQIVmXSNv1JGXUdAT1mYnqg0ubHzYw2sX8z93x0n+kjeY0VO2OJ2GpqiY4nCxMbRbrJrflBFu8hYTtybRjLgaXuXGw5k6k8/FUcpaeQtdjOIknMuLnG+tydTmT6KKFKWt+0eGNF7N1ef2Xqdz2gNDu82P7Lk6RzmnESbC4TQbTSg5MBA5k3JSwCRxswOd4C6dbLoeueNTnbCOK2jdDo7gwNdO7E4i/VNNmjxPFBklFQ1J+5bl2m/RO6ijkYLyxkDi8ZgeP7kea+iqfdqnjFmRMb4NF/VMtqbMo2ENmkgjLhkHvYwnwBKaGEQbv42tMcnU1DheBr8IgqBe2GOX4Wvvlhd2ScsQOSZCkfI58bmmOpiaSWG7H9gXeLHUgXJbra9lqm2d1I4oZpqR8MlPYyT0rpAad1hm+Nwv1MttHDI6EEZLP95pS2RsLnuklibZ7nNY6SFhzbDI5ty97RrqG/CNLCq5bD21UULw+Kd4eQCY2DJ3c8ZtcPL0VoZv/tmqkbHA+ON7zZkbGRXPddwdn5qjGWJgiLXPElz1guCDYgskjeLag6HMFp4EWcS1DZc4ntFRk8FvYZKf05dVPloOy7uJzg0GTbu8lP2pWyOaNQaeJ7fMtYLeqe7I6V7ODK2DCb2Mkdx6sJN/I+Sid3t6qmYU0NS+3WgiOe/x4TZzSfxg2B45i+oJu1ZuPT1TLStu4j/MGTx5/Q5Ii1bI2tBVRiSCRsjDxBzB5Eag9xUgsI2nsmv2BM2eF5kp3ODessS3XJkreF+B9LHJaxufvPFtGASR9l4sJI73LT9QeBQWBCEqACVCEAhCEHpIEqEHkpUJUCKgdNdd1ezBGNZ6hjD+lgdIfdjR5q/rKOnOQl2z4uDnyOI/qiaPmVJWE90V0HVUl+JDR7XPzV2UHubHhpG2/EfoPoum+O0zSbOrKhvxR07yz9ZGFn/ItVhGMb3babV19bUl12U5FPTi+WFhfd3m5rj/AFBVqweW9Y7DhaC9oIGbhcC58bLxSR9VGLtx9Y0OtcXtYWBv6+a51IxHEWPAJv8A5ZdblfASrAZ7Rb9oGts7CwXsO7ET7ppVDAS24JsdL2PhddnPHWPdiLbghovhdY5C4OenBMq5935aBoGt+/6oJndg2a4jXF9AtC3Z3ilge3E4uZxBNyPBZ/u42zL8yfnb6Ky0yg1vfPeoUuzxLG77apHV02V7OLbl/g0XPjYcViFXtd73OdO98jnnN+Tnk959F03t2zI8sGLs0zBBGDmLuON59MI/pC87uRRyVNNFLG9wL2B4a4AuLnAkZg81YjYa7Uc2FsLmuJdI0ucLFpbY218lHOqHOuWEi/xi+ud8zzurT0pUUTNqTRQtwxwtY0N0sXNDj8wqnALXSQ2e8sfiABLTctLQW2IscuRuQfVeoJWMcHOZjif2ZGXAcO9p4OGoP0uvFTfrMhfs6dwBv7BJCB/S7LwPAqCy0DxG91NNJemqcM0NVnZj82w1YGosQWSN1ti4tC2To+3ldPGaapGCqgcYpGk6luXnzB4rD9mgyRGltdwc6Wkv92UD7WD9MjRkPxNbzKlNgbcLJ4JcRvgbE9183NblE8n8TRZhPJg5lB9GVdLHNG+KVrXxyNLXsIuCDqCsQmjl3e2sMJc6mk7TCfvxOObT+Zpy8QD96y2LYG0xURA/fGTh381W+l/YoqNnOmaPtKR3XA8cGko8LdrxYFYF2o6hssbJGHEx7Q5p5gi4XcLPehrbBmonQOPap3WH6XXIHkbrQgoFQhCAQhCD0hCVAiEIQIsl6Zhet2aOFj/9rVrSyrpqZafZ0nAGQf7Xxn6qSsLxuof/AE9vwvcPkq302VBbsksGs1TFH42Jkt/wCsO6xsJW8ng+o/hVXppIMWzo+dcZSO6OF5PzVRlFaLPwjRgDR5C30XvFYLi83eT3r3KckEXXuve+ainMba9hmfBSFY5R5zsFRM7Ddmxtss+PiVY4jbPgM1B7Dj7RP4W29f7KmKk2iefy29clBVZXunlawC5fKSBxu8/QK07pwVDa2OVkeLBKH8LDCb3tyFr+Sh4x2geI0PFS1DtaanOOJ9nD8QDx6FeonSI/fHabpq+sk1x1L/Rpwt9mhRMWYv3pxUHE4uJJJJJ8SblcwFFJs5mKsjHJpP8AxK5bToupkcwfC4Y4/C+bfI/RPNgx/wDrL8Ordb0H8qU3gpcUWIDtRHGPAfEPT5KCv0sx7D2uLXEjtcWyMN2PHt6J/tMNE3WNAayoaKhrRo0uJbKwfpka8f0qLDc3sGjhjb5Z/K6eun6ynF/ihlJ/plaLj/cx58XlRWvbi7VLWQS3u1w6qX9TMifMWPmtKq4GzRPjdYsljcw8iHNIPzWGdHFXiEtOT8YEjO57bg+ostg3Xreshwn4o8vLgqjLOhmodFtGSBx+KN8bv1Ran1Y71W42WD7idneKYDQVtU3y6yULeUnvAhLdCBEL1ZCBUIQgEiVIgFnPTdTXo6eQD4KgsvyD4nH5sC0dVnpHouu2XUt4xtbMP/jcHO/4hykrDlupUB7g4aTU7JR5ta7/ALFU3pxjdI+kYx2AxxSy3zGrmN4KV6Pa3FS0jj8UT30r/wClxw/8Xt9FG9MxtLD3w29HuJ+isIzFsLT8L3tPfZw90TRS2ydG7yLT8yvcAXKtdkp0z5u/+sxW/Uw1n03X7TMfRF1cUg1Z6EEJi02OeSdTSkaEjzK50shxtOpDg4X0yN807WGScE+CJj2n8LJsBoMbnCxBda4z0H8p3tU2iA/E8D0z+ic00rnxsc4AFwvYXt3ey4bUgc5ow27N3Yb2Jy4KsIiZnUIqIZrrOclxY/D8TXt/pP0STVDTo4eqbe74r08dZj1jRq85pEOKR2irNIbsR3me78MdvVw/YqfkZfXQ6qM3Si7Mz+bmt9AT/wBlMvCgz6qb1UuH/TeW+V8vYrpSf+6zmw2/vzKcbzxWnJ/E1rvP4foE1pviv+S3v/KirBuPUltS2x4H5X+i2DYFaI6tudmSjy7WfzWK7sNwVTG8j8x/Ku23NqdVTueDZzWkNPG509yiOPRf9ttuSUaOnqJvJznuHzW8rG+gjZ/bmmIybGGA95P7BbMFQlkWSoQIhKlQCEIQCEIQIvE8LXtcxwu17S1w5hwsfYrohBkG6THU1VV0TzmR17MrXfC4wzetg/wsozpXrzLUxXuAIGgXBAJuS63PM2Vl6Rac0lZDtBjbhrxM8DiA0RVLfOIscB/43lUjfHbrppDARHJBFK8wktIfZ1hmeRsOCkNcdaWvEXt0xP8AnW9fKFfh0TStcnfUstkHM8HED0TKphHCX/cB/CnV8HXPA3248lLfPU+1tIicr1RNuUVEDhyPeFIbBoXvcHBvYa8YnXbwsdL3XqJiXJkw3x+KNLcyPC1rfwtA9Aoja7vtQPwtHvcqZuoCudeaQ/mt6ABGT3BIed1zqpRxa0+IXqNNakqdMOunP5NI1GSdeW9x7S4PEZ0ZhPcVzwgWuX2vnYgm3ddegiXROktzL38UVn/mP20uOyYohC0wYsDiXdr4r6G/ouzwvGzGYaeEf+MH1F/qvchVcio73N7cZ/IR6Efuoqm+8eTf5+ilN7HXczuafcj9lGQ5DxcAPM/sCoJrYedaLcZGtHmWtXfeisxmOEcXYneA09/km+6hAqIXuIA6+MknIWD7knyC67s0Rra5mpD3i36AdfP6qjcuizZP+G2dGSLOmPWHnbRquS5U8QYxrGiwa0NA7gLBdUAhCEAhCVAJEqEAhKkQIhKhBC727PbPSSA4QYh1zC4XbdgOJpH4XML2HueV861rsUpPevpDeiCWSiqI4G45XxFjW3Db4iAcybaEr5sebSEOyIJGqLFZmN67nR+QUPWFS07slC1blUMXnNT27Vw/U2DSSLmx0GYUAdVYtiCzXHwCCwseoaohxOLonCQEkn7pBv7p0Z7NceTT8lGRZWUa4bY623kr1R5ROvrqXXE4fExw8rj2TSaQHj9FJdaQNU0lqzxDT5Kf3Ozp4N+616+sRMfSYNGJZGXsBqSAPPJe3Wd90BPdiPY2ZrXRNfjIwuJzYRc3Atn/AAkTPk58uHFWJmmTq+UwtbxYADQCw8k0qH2C6zSKNrpwAq5la26/HPhHANb56/UJpVsLCxvdjvwOZaCP9p912iOJz5SCQCSBzPAJ1VMx1WDItgYyInhdg7frIXoObo7R4NOxd39RA+QctV6I9hWkErm2Ibi00GjR8ys82HTdfLiPwlwIHMDJt/mvoXc3ZnUUzSRZz+0foEE+hCVAIQhAIQhAqEJUCIQlQIhCEFH6WNpyQUcYikkie+a+KOR8b7NabjE0g2uRksTZITrZ3iAVpXTVV/aQRX+GMuI/U63/AFWaRaJrbfByc2Cd47TXfk4ztYBm236SW+yjKljDo4g8iPqpCscoaoKnS2vzr5P1K1t8emIn3jTkW2OoNlKbPqcsuKjINUkU5a830J//AAquS0xPdGlifLdp78l5iGaaxSXtxT5kTtQA4dzgm9NsHFzZ9/y69WvL8PUuiYSJ3M82za4eR+iZOKbh4vgy4+y9Zj1iXtgT7Y7ft2n8LXH6fVMo1IbNyc4/lA9T/CrJKVMqgtqTl56turte4cSV02rtHCcDe1IdG/UqNjNg4YrnWaXl+Ud/yUHR0rY8OEXDDiA5kHLxu63oVze0sYI73lmN3HjY/ET7+6IAM5nizR/lt+X9+akd2tmSVlQ0NaS6Q4WDk3ie5BfOjDd3rXh7h9myxPgNAtqY2wsOCjd39kspYGRMGYAxHmVJhAqVCECJUIQCEIQKhCEAhCECISpCbaoMa6Y6CUVLZyWOjkAYxrSS9uBoviFssz7rPWvA42PI5FSO8U4kq55RkZJXPJGV7uJz5pi5xtmQRyIBUnbsxV4lqRF7Wrbz1Ex94n7o+sKip1K1DmcWW/Sfoo6ZrOBPgU38HnJx6R20yRaPnE+0w4R34Zk5AJsQdOXDiFN7O2dDMxwMjxKLnBh7OHLO9s8zzTCsp5IzZ4xgfezuPE6jzyVc0xo52MSQ6+gNgpiJRmyG9i/Mn9vopSMKpHYSWUjQlNX1BOoafJdagpspqHVXm8isajJOvWXRoJ0AC41u0pGDqwGh1rl4FgBwy5p3AFB7SN5X30BtbwCkREM8vIyZddc70ICSTZ1vxynXyTyJrXAD4YmZhvF35ndyjw69r5NGnLyHErtEHSkNaCGk6DNzv79AqxPY2uqZA1oOAECw1JOgHeV9BdHO6AoohLK0f4iRuY/028Gjv5qG6Kdy2Qxsq52gykXiZq1g/F3u7+C04BABegkSoFQhCAQhCAQhCBUIQgEIQgFH7fqeqpKiT8MLyPHCQPchSCr2/NdTw0UhqhM6KQiMtht1lz2ha5At2UHz5UG7ye9eJTYL1cEnCQc9L2d5grlO42za4eX7JuHXfg8ikb6JmPOO2PeNwjKkpi7VPJym0bbuVcmtJ/dmn/zHcmhvqbn5BPqujDtR4HQpxu7BaEn8Tz7AD90+MPFQVWKENc5gIAByuMNzxseKcYXDVp8RYrlrc8yT6roBYZZeBI+STt2YcnG6enLSd+cT+0xMfY2nf4jxBC4tTp9Q4cb+IBXIyX4N9LKdr3OPh28OS0etfxLvTquvjc6R1gSXOJHHUqx0kDnPaBgsXAEFt8jy5Kwx0LW/dHjZWHJkrSttUt1R560p9Fu9I+xkOActXfsFdt1d3muljjY34iMTjmbDmV0pqa5V+3C2b2zIRoLBGa70kAYxrRkGgBdwkCUIFQhKgEIQgEIQgEIQgVCRCBUJEIArOemWqtBTxfic558gAPmVoyx3pjqsVUyP/TiaPNxLvqEGeNaDqAfEXXGo7PwkjzNvQ5Jw3RNKoqvdMl6TuszHp2GU8x+9Z3iLH2XKnJxAtDbgg2dctyPEckTrvs+O58cvVTUPduRlv4rb9e1eKE3jYcLWktDi1uTQTmbLvVtwxSO5McfZLTsAsOAFl42xU9XA8hrXk9kNdfCb5Z24IxVEDRe3aIc4ON8ozrhscA8Dc5eJQ5jiMsLu8OCbh3R/Ds9o3jiLx/rMT9O8zkSxhK+N3FpSxJuGFuNmr4qTHylMbEjvI3uufQKw9Vc2UXsCAgl5BAwdk2Njc8OeisMEaMO51oqbOy0/d+jEULRaxIuVS92qLrJRyvc+S0ZjbABB6SoQgVCEIBCEIBCEIBCEIBCEiBUiEiBVnvS/SQmGB5Y3rTMW9Zazy0RuyJGouQVoKzTpml7NM29rNld7sH0QY07aGFxaW3AJFx48lylq2u5jxC5P4lNiFUdHuBORupbYcN5GZZB1z5ZqHoY7yDu7R8s1ct3KNzmsaxpc6Q3AAuSTp7JKpeK5ybqUu+Ox5KelgfLk6aU2ZxDWsJJPmRktF3V3VFOBLMA6XVrdQ3v7yql0yzXnpY/wwvfb9bgP+ikDOmrnMByF+fFdQFymVHDrHcHH1v8ANemsLvvH2/Zc05pwpqG0cnNEai8+8rHuzAWscMT3AuFg517ZZ25KzwxqL3dp/sg7mSfe30Vjo4Lua0cSjKZmZ3K27pUOBheRmcgrEAuNFDgY1vILuiBKhCAQhCAQhCAQhCAQhCBEISIBCLJUHlQm9O78VdEWvaMbQerfoRfUX5FTZXkhB84bf3RlpS5sjJGC+TiMTD4OGXuoIbKdfItIHiF9Q19FHM0tkaHA8CqtN0eUT33wPH5WuLQrsYlsnYlRUyf4elj6yV9gbfCxpOb3u0aP7Fzkt83U3UhoIxpJOW2dLbT8rB91vueKlNkbFgpI+rp4mxtOZsMyebjqSnpUHm6pfSfsqB9I6ocz7aMWZICQbWc7CeYv81dgFUek91tnvHM/S31QYZNPgzcPQ3XB9Uw8fYpdocAUyDrEkC+SocxuB0IPmpCijLnANBJ4AAk+iYUT8VyRbgrbuZBeR7z91th4uP7D3QWzZdLgijaciGi/jbNWTdqmxzX4NUPTsc8hrRclXfd/Z/VR3PxO1UEsEqRKgEISoEQlSIBCVCBEIQgEIQgRCVIgEiVCBEll6Qg84UtkqECELwWLqhBxLVD7w7IZVwPhkvZwycNQQbghThC8liD5z3p3Wkp5MLzYDIEjsnkQeIVebSObyPmvqKu2bFM3BKxr2ng4AqnbT6NaOQ4ow+I8muu30KDGYKKS18PxaZhXrdbZjo47OHae69hnwAAVwoOj6FhBMjj4jNWXZ2xYoc2tuRoTmg47A2OImYnjtu9hyU2EgShAoSpEqASpEIFQkQgEqRCBUiEIBCEIBIhCAQlQgRKhCBEIQgEqEIPJQhCAXhyEIPLV0CEIAJQhCBQlQhAIQhAICEIBCEIBCEIBCEIP/9k="
                  alt="Xà Đơn Treo Tường"
                  class="max-h-full object-contain"
                />
              </div>
            </template>
            <template #title>
              <h3 class="text-xl font-bold text-[#002855]">Xà Đơn Treo Tường</h3>
            </template>
            <template #content>
              <p class="text-gray-600 text-sm leading-relaxed mb-4">
                Công cụ tuyệt vời để kéo giãn cơ sống, hỗ trợ phát triển chiều cao hiệu quả ngay tại nhà.
              </p>
              <div class="flex items-center justify-between">
                <span class="text-2xl font-bold text-[#002855]">450.000đ</span>
                <Button
                  icon="pi pi-shopping-cart"
                  severity="secondary"
                  outlined
                  rounded
                  @click="addToCart('xadon-treo-tuong')"
                  class="hover:bg-[#002855] hover:text-white hover:border-[#002855]"
                />
              </div>
            </template>
          </Card>
        </div>

        <!-- Product Card 2 -->
        <div class="col-span-12 sm:col-span-6 lg:col-span-4">
          <Card class="product-card h-full hover:shadow-xl transition-shadow duration-300">
            <template #header>
              <div class="relative bg-gray-100 p-8 flex items-center justify-center h-64">
                <img
                  src="data:image/jpeg;base64,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"
                  alt="Dây Nhảy Tốc Độ"
                  class="max-h-full object-contain"
                />
              </div>
            </template>
            <template #title>
              <h3 class="text-xl font-bold text-[#002855]">Dây Nhảy Tốc Độ</h3>
            </template>
            <template #content>
              <p class="text-gray-600 text-sm leading-relaxed mb-4">
                Bài tập cardio toàn thân, kích thích xương phát triển với cơ thể luôn ưu tiên.
              </p>
              <div class="flex items-center justify-between">
                <span class="text-2xl font-bold text-[#002855]">150.000đ</span>
                <Button
                  icon="pi pi-shopping-cart"
                  severity="secondary"
                  outlined
                  rounded
                  @click="addToCart('day-nhay-toc-do')"
                  class="hover:bg-[#002855] hover:text-white hover:border-[#002855]"
                />
              </div>
            </template>
          </Card>
        </div>

        <!-- Product Card 3 -->
        <div class="col-span-12 sm:col-span-6 lg:col-span-4">
          <Card class="product-card h-full hover:shadow-xl transition-shadow duration-300">
            <template #header>
              <div class="relative bg-gray-100 p-8 flex items-center justify-center h-64">
                <img
                  src="data:image/jpeg;base64,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"
                  alt="Thảm Tập Yoga Định Tuyến"
                  class="max-h-full object-contain"
                />
              </div>
            </template>
            <template #title>
              <h3 class="text-xl font-bold text-[#002855]">Thảm Tập Yoga Định Tuyến</h3>
            </template>
            <template #content>
              <p class="text-gray-600 text-sm leading-relaxed mb-4">
                Chất liệu êm ái, chống trượt, giúp thực hiện động tác kéo dãn cơ và yoga an toàn.
              </p>
              <div class="flex items-center justify-between">
                <span class="text-2xl font-bold text-[#002855]">320.000đ</span>
                <Button
                  icon="pi pi-shopping-cart"
                  severity="secondary"
                  outlined
                  rounded
                  @click="addToCart('tham-tap-yoga')"
                  class="hover:bg-[#002855] hover:text-white hover:border-[#002855]"
                />
              </div>
            </template>
          </Card>
        </div>
      </div>

      <!-- View All Button -->
      <div class="text-center mt-12">
        <Button
          label="Xem Tất Cả Sản Phẩm"
          icon="pi pi-arrow-right"
          iconPos="right"
          class="bg-[#002855] text-white font-medium px-8 py-3 hover:bg-[#003D7A] transition"
          @click="viewAllProducts"
        />
      </div>
    </div>

    <!-- Toast for notifications -->
    <Toast />
  </section>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import Card from 'primevue/card';
import Button from 'primevue/button';
import { useToast } from 'primevue/usetoast';

const router = useRouter();
const toast = useToast();

const addToCart = (productId) => {
  toast.add({
    severity: 'success',
    summary: 'Thành công',
    detail: 'Đã thêm sản phẩm vào giỏ hàng',
    life: 3000
  });
  
  // Logic thêm vào giỏ hàng
  console.log('Added to cart:', productId);
};

const viewAllProducts = () => {
  router.push({ name: 'Products' });
};
</script>

<style scoped>
.product-card {
  border-radius: 12px;
  overflow: hidden;
  border: 1px solid #e5e7eb;
  transition: all 0.3s ease;
}

.product-card:hover {
  transform: translateY(-4px);
}

:deep(.p-card-header) {
  padding: 0;
}

:deep(.p-card-body) {
  padding: 1.5rem;
}

:deep(.p-card-title) {
  margin-bottom: 0.75rem;
}

:deep(.p-card-content) {
  padding-top: 0;
}

:deep(.p-button-rounded) {
  width: 3rem;
  height: 3rem;
}

:deep(.p-button-outlined) {
  border-width: 2px;
}

/* Responsive adjustments */
@media (max-width: 640px) {
  .product-card {
    border-radius: 8px;
  }
  
  :deep(.p-card-body) {
    padding: 1rem;
  }
}
</style>