public class TemperatureEnvironment extends Environment {

  // action literals
  public static final Literal hotAir = 
    Literal.parseLiteral("spray_air(hot)");
  public static final Literal coldAir = 
    Literal.parseLiteral("spray_air(cold)");

  private double temperature;

  @Override
  public void init(final String[] args) {
    if (args.length >= 1) {
      temperature = Double.parseDouble(args[0]);
    } else {
      temperature = RAND.nextDouble() * 20 + 10;
    }
  }

  @Override
  public Collection<Literal> getPercepts(String agName) {
    return Collections.singletonList(
        Literal.parseLiteral(
          String.format("temperature(%s)", temperature)
        )
    );
  }

  private static final double FAILURE_PROBABILITY = 0.2;

  @Override
  public boolean executeAction(String ag, Structure action) {
    boolean result = true;
    if (RAND.nextDouble() < FAILURE_PROBABILITY) {
      result = false;
    } else if (action.equals(hotAir)) {
      temperature += 0.1;
    } else if (action.equals(coldAir)) {
      temperature -= 0.1;
    } else {
      RuntimeException e = new IllegalArgumentException();
      logger.warning(e.getMessage());
      throw e;
    }
    return result;
  }
}
