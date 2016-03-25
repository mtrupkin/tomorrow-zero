package org.mtrupkin.random

/**
  * Created by mtrupkin on 3/24/2016.
  */
/**
  * Created by martin on 20/10/15.
  */
trait PerlinNoise extends MathUtils {


  //======================================================================
  // Private Constants for some reason it doesnt work if they are not lazy
  //val permutation = Array.fill(300){ random(255).toInt }
  //val permutation = (1 to 300).map(_=>rand(255).toInt)
  //permutation.foreach(p=>print(s"$p, "))
  val permutation = List(0, 54, 140, 78, 171, 189, 92, 157, 249, 131, 178, 116, 116, 215, 192, 122, 138, 29, 47, 78, 165, 147, 130, 246, 149, 21, 208, 40, 166, 36, 178, 121, 96, 167, 111, 43, 74, 174, 52, 240, 232, 172, 110, 93, 218, 37, 169, 79, 84, 195, 212, 172, 244, 70, 103, 80, 202, 67, 187, 199, 87, 74, 219, 230, 135, 30, 27, 205, 239, 38, 23, 13, 6, 71, 59, 51, 185, 120, 69, 207, 142, 71, 73, 81, 80, 134, 78, 226, 138, 3, 222, 128, 55, 227, 1, 0, 96, 246, 111, 189, 63, 150, 100, 233, 189, 88, 252, 56, 141, 234, 26, 219, 152, 227, 118, 56, 44, 59, 254, 174, 71, 83, 164, 81, 32, 92, 89, 7, 123, 174, 146, 199, 115, 129, 83, 83, 247, 13, 201, 148, 187, 181, 144, 113, 85, 95, 211, 125, 234, 76, 24, 54, 53, 103, 215, 36, 147, 218, 226, 54, 156, 251, 79, 2, 221, 173, 196, 151, 118, 32, 24, 176, 219, 185, 139, 176, 97, 179, 107, 5, 246, 127, 126, 205, 77, 173, 122, 197, 161, 109, 158, 98, 144, 29, 192, 21, 151, 166, 78, 117, 121, 126, 58, 201, 66, 75, 73, 24, 124, 10, 32, 92, 26, 147, 12, 44, 141, 9, 218, 26, 21, 112, 15, 212, 58, 111, 50, 193, 128, 117, 135, 76, 157, 41, 13, 203, 198, 60, 127, 3, 66, 231, 50, 234, 21, 156, 119, 53, 124, 95, 228, 150, 215, 65, 242, 107, 231, 64, 73, 170, 116, 155, 150, 140, 3, 88, 242, 99, 208, 44, 183, 108, 23, 165, 5, 79, 127, 153, 137, 15, 232, 84, 188, 248, 159, 34, 70, 252, 225, 223, 104, 88, 200, 82, 253, 99, 167, 220, 8, 91, 15, 151, 33, 80, 84, 193, 152, 204, 209, 176, 122, 192, 245, 38, 147, 106, 50, 223, 214, 10, 27, 151, 92, 71, 64, 49, 217, 189, 105, 156, 161, 222, 234, 21, 63, 184, 13, 38, 188, 132, 83, 66, 117, 156, 144, 194, 21, 37, 105, 232, 24, 156, 178, 213, 161, 184, 180, 99, 9, 16, 239, 161, 210, 119, 124, 218, 25, 167, 101, 56, 150, 60, 185, 152, 156, 45, 56, 164, 75, 191, 125, 244, 61, 45, 188, 238, 176, 139, 191, 26, 237, 251, 243, 188, 82, 200, 188, 18, 231, 231, 37, 182, 233, 81, 78, 208, 237, 152, 163, 248, 166, 173, 245, 53, 97, 220, 22, 41, 118, 220, 122, 42, 211, 117, 182, 12, 170, 12, 75, 109, 35, 155, 116, 162, 142, 168, 23, 166, 74, 64, 235, 56, 222, 108, 65, 194, 124, 95, 145, 218, 40, 74, 34, 202, 42, 213, 151, 48, 128, 102, 66, 178, 10, 137, 85, 166, 39, 111, 249, 200, 77, 229, 78, 96, 236, 14, 37, 189, 134, 246, 13, 108, 137, 77, 216, 133, 250, 160, 119, 36, 47, 15, 63, 118, 102, 184, 90, 190, 1, 19, 23, 101, 94, 16, 61, 111, 72, 14, 112, 174, 216, 164, 203, 168, 129, 215, 167, 101, 157, 136, 103, 87, 166, 194, 187, 30, 140, 68, 155, 37, 72, 131, 108, 2, 1, 177, 156, 27, 92, 84, 228, 41, 211, 160, 11, 66, 217, 54, 187, 108, 171, 182, 133, 5, 129, 109, 146, 35, 158, 108, 238, 119, 109, 173, 182, 136, 110, 11, 83, 203, 76, 140, 109, 172, 192, 12, 215, 158, 85, 38, 226, 63, 48, 79, 46, 208, 100, 24, 213, 146, 22, 243, 97, 35, 211, 57, 135, 150, 100, 210)

  // Initialize the p array with two copies of the permutation array.
  val p = permutation++permutation

  /**
    *  Three dimensional Perlin noise.
    *  Based on Ken Perlins reference implementation of improved noise ( http://mrl.nyu.edu/~perlin/noise/ ).
    *
    * @param x coordinate to calculate the noise value at
    * @param y coordinate to calculate the noise value at
    * @param z coordinate to calculate the noise value at
    *
    * @return the noise value at the specified point, a value between -1 and 1
    */
  def noise(x: Double, y: Double = 0, z: Double = 0): Double =
  {

    def fade(t: Double): Double =
    {
      // fade(t) = 6t^5 - 15t^4 + 10t^3
      t * t * t * (t * (t * 6 - 15) + 10)
    }

    def interpolate(t: Double, a: Double, b: Double) : Double =
    {
      a + t * (b - a)
    }

    def grad(hash: Int, x: Double, y: Double, z: Double): Double =
    {
      // Convert low 4 bits of hash code into 12 gradient directions
      val lowBits = hash & 15
      val u = if (lowBits < 8) x else y
      val v = if (lowBits < 4) y else
      if (lowBits == 12 || lowBits == 14) x else z
      return (if ((lowBits & 1) == 0) u else -u) +
        (if ((lowBits & 2) == 0) v else -v)
    }



    // Calculate integer values for coordinates
    val roundedX = x.toInt
    val roundedY = y.toInt
    val roundedZ = z.toInt

    // Find unit cube that contains point
    val X: Int = roundedX & 255
    val Y: Int = roundedY & 255
    val Z: Int = roundedZ & 255

    // Find relative x,y,z of point in cube
    val x_ = x - roundedX
    val y_ = y - roundedY
    val z_ = z - roundedZ

    // Compute fade curves for each of x,y,z
    val u = fade(x_)
    val v = fade(y_)
    val w = fade(z_)

    // Hash coordinates of the 8 cube corners,
    val A = p(X) + Y
    val AA = p(A) + Z
    val AB = p(A + 1) + Z
    val B = p(X + 1) + Y
    val BA = p(B) + Z
    val BB = p(B + 1) + Z

    // and add blended results from 8 corners of cube
    interpolate(w,
      interpolate(v,
        interpolate(u,
          grad(p(AA), x_, y_, z_),
          grad(p(BA), x_ - 1, y_, z_)),
        interpolate(u,
          grad(p(AB), x_, y_ - 1, z_),
          grad(p(BB), x_ - 1, y_ - 1, z_))),
      interpolate(v,
        interpolate(u,
          grad(p(AA + 1), x_, y_, z_ - 1),
          grad(p(BA + 1), x_ - 1, y_, z_ - 1)),
        interpolate(u,
          grad(p(AB + 1), x_ , y_ - 1, z_ - 1),
          grad(p(BB + 1), x_ - 1, y_ - 1, z_ - 1))))
  }
}

case class Perlin(min:Double, max: Double) extends PerlinNoise with MathUtils {
  override def noise(x: Double, y: Double = 0, z: Double = 0): Double = {
    map(super.noise(x,y,z),-1,1,min,max)
  }
}

object Perlin extends PerlinNoise {
  def apply(max: Double):Perlin = Perlin(0,max)
  def apply:Perlin = Perlin(-1,1)
}